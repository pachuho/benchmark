package com.pachuho.benchmark.data_test

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import com.pachuho.benchmark.core.data.api.JHApi
import com.pachuho.benchmark.core.data.auth.TokenAuthenticator
import com.pachuho.benchmark.core.data.interceptor.AuthInterceptor
import kotlinx.coroutines.test.runTest
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Test
import org.junit.Assert.*
import org.junit.Before
import retrofit2.Retrofit

class JWTTest {

    private val mockWebServer = MockWebServer()

    private lateinit var fakeTokenProvider: FakeAuthTokenProvider
    private lateinit var fakeEventBus: FakeEventBus
    private lateinit var api: JHApi

    @Before
    fun setup() {
        mockWebServer.start()

        val retrofit = Retrofit.Builder()
            .baseUrl(mockWebServer.url("/"))
            .addConverterFactory(Json.asConverterFactory("application/json".toMediaType()))

        fakeTokenProvider = FakeAuthTokenProvider(
            accessToken = "access_token",
            refreshToken = "refresh_token",
            reissueResult = true,
            retrofit = retrofit.build()
        )

        fakeEventBus = FakeEventBus()

        val authInterceptor = AuthInterceptor(fakeTokenProvider)
        val authenticator = TokenAuthenticator(fakeTokenProvider, fakeEventBus)

        val okHttpClient = OkHttpClient.Builder()
            .addInterceptor(authInterceptor)
            .authenticator(authenticator)
            .build()

        api = retrofit
            .client(okHttpClient)
            .build()
            .create(JHApi::class.java)
    }

    @After
    fun tearDown() {
        mockWebServer.shutdown()
    }

    @Test
    fun `AccessToken이 유효하면 디바이스 목록을 정상적으로 받아온다`() = runTest {
        val responseBody = """
            [
                {
                    "deviceId": "a",
                    "productType": "PLUG",
                    "productId": "asd",
                    "name": "plug",
                    "online": true,
                    "status": {}
                }
            ]
        """.trimIndent()

        mockWebServer.enqueue(
            MockResponse()
                .setBody(responseBody)
                .setResponseCode(200)
        )

        val result = api.getDevices()

        val recorded = mockWebServer.takeRequest()
        assertEquals("Bearer access_token", recorded.getHeader("Authorization"))
        assertEquals("plug", result.first().name)
    }

    @Test
    fun `AccessToken이 만료됐을 경우 refreshToken으로 재발급 후 재요청된다`() = runTest {
        // 1: accessToken 만료
        mockWebServer.enqueue(MockResponse().setResponseCode(401))

        // 2: accessToken 재발급 성공
        mockWebServer.enqueue(
            MockResponse()
                .setResponseCode(200)
                .setBody("""{"accessToken":"new_token","refreshToken":"refresh_token"}""")
        )

        // 3: 재요청 성공
        val deviceListBody = """
            [
                {
                    "deviceId": "a",
                    "productType": "PLUG",
                    "productId": "asd",
                    "name": "plug",
                    "online": true,
                    "status": {}
                }
            ]
        """.trimIndent()

        mockWebServer.enqueue(
            MockResponse()
                .setResponseCode(200)
                .setBody(deviceListBody)
        )

        // API 호출
        val result = api.getDevices()

        // 결과 검증
        assertEquals("plug", result.first().name)
        assertTrue(fakeTokenProvider.reissueCalled)
        assertFalse(fakeTokenProvider.logoutCalled)

        // 요청 로그
        repeat(3) { i ->
            val request = mockWebServer.takeRequest()
            println("Request #$i: ${request.method} ${request.path}, Authorization=${request.getHeader("Authorization")}")
        }
    }
}
