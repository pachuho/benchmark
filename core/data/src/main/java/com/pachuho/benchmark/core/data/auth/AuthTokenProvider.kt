package com.pachuho.benchmark.core.data.auth

interface AuthTokenProvider {
    suspend fun getAccessToken(): String?
    suspend fun getRefreshToken(): String?
    suspend fun reissue(): Boolean
    suspend fun logout()
}