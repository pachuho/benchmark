package com.pachuho.benchmark.core.data.auth

import com.pachuho.benchmark.core.data.api.JHApi
import com.pachuho.benchmark.core.data.api.model.request.ReissueRequest
import com.pachuho.benchmark.core.data.mapper.toDomain
import com.pachuho.benchmark.core.datastore.datasource.AuthTokenPreferencesDataSource
import kotlinx.coroutines.flow.firstOrNull
import javax.inject.Inject
import javax.inject.Provider

class AuthTokenProviderImpl @Inject internal constructor(
    private val preferencesDataSource: AuthTokenPreferencesDataSource,
    private val jhApiProvider: Provider<JHApi>
) : AuthTokenProvider {

    private val api: JHApi
        get() = jhApiProvider.get()

    override suspend fun getAccessToken(): String? {
        return preferencesDataSource.authTokenFlow
            .firstOrNull()?.accessToken
    }

    override suspend fun getRefreshToken(): String? {
        return preferencesDataSource.authTokenFlow
            .firstOrNull()?.refreshToken
    }

    override suspend fun reissue(): Boolean {
        val refreshToken = getRefreshToken() ?: return false

        return try {
            val response = api.reissue(ReissueRequest(refreshToken))
            val newToken = response.toDomain()
            preferencesDataSource.saveAuthToken(newToken)
            true
        } catch (e: Exception) {
            false
        }
    }

    override suspend fun logout() {
        preferencesDataSource.clearAuthToken()
    }
}
