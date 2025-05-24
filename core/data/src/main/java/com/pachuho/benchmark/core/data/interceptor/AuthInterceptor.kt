package com.pachuho.benchmark.core.data.interceptor

import com.pachuho.benchmark.core.data.auth.AuthTokenProvider
import kotlinx.coroutines.runBlocking
import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject

class AuthInterceptor @Inject constructor(
    private val tokenProvider: AuthTokenProvider
) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()

        val accessToken = runBlocking { tokenProvider.getAccessToken() }
        val authRequest = if (accessToken != null) {
            request.newBuilder()
                .addHeader("Authorization", "Bearer $accessToken")
                .build()
        } else {
            request
        }

        val response = chain.proceed(authRequest)

        if (response.code == 401) {
            response.close()
            val refreshed = runBlocking { tokenProvider.reissue() }

            return if (refreshed) {
                val newToken = runBlocking { tokenProvider.getAccessToken() }
                val retried = request.newBuilder()
                    .removeHeader("Authorization")
                    .addHeader("Authorization", "Bearer $newToken")
                    .build()
                chain.proceed(retried)
            } else {
                runBlocking { tokenProvider.logout() }
                throw AuthTokenExpiredException("Refresh token expired")
            }
        }

        return response
    }
}

class AuthTokenExpiredException(message: String) : Exception(message)