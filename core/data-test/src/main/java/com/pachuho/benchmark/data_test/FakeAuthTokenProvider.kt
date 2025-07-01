package com.pachuho.benchmark.data_test

import com.pachuho.benchmark.core.data.api.JHApi
import com.pachuho.benchmark.core.data.api.model.request.ReissueRequest
import com.pachuho.benchmark.core.data.auth.AuthTokenProvider
import retrofit2.Retrofit

class FakeAuthTokenProvider(
    private var accessToken: String?,
    private val refreshToken: String?,
    private val reissueResult: Boolean,
    private val retrofit: Retrofit
) : AuthTokenProvider {

    var reissueCalled = false
    var logoutCalled = false

    override suspend fun getAccessToken(): String? = accessToken

    override suspend fun getRefreshToken(): String? = refreshToken

    override suspend fun reissue(): Boolean {
        reissueCalled = true

        return if (reissueResult && refreshToken != null) {
            try {
                val api = retrofit.create(JHApi::class.java)
                val response = api.reissue(ReissueRequest(refreshToken))
                accessToken = response.accessToken
                true
            } catch (e: Exception) {
                false
            }
        } else {
            false
        }
    }

    override suspend fun logout() {
        logoutCalled = true
    }
}
