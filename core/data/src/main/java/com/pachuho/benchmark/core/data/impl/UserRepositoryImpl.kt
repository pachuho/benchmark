package com.pachuho.benchmark.core.data.impl

import com.pachuho.benchmark.core.data.api.JHApi
import com.pachuho.benchmark.core.data.api.extension.safeApiCall
import com.pachuho.benchmark.core.data.api.model.request.LoginRequest
import com.pachuho.benchmark.core.data.mapper.toDomain
import com.pachuho.benchmark.core.datastore.datasource.AuthTokenPreferencesDataSource
import com.pachuho.benchmark.core.domain.repository.UserRepository
import com.pachuho.benchmark.core.model.AuthToken
import com.pachuho.benchmark.core.model.ResultWrapper
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

internal class UserRepositoryImpl @Inject constructor(
    private val authApi: JHApi,
    private val preferencesDataSource: AuthTokenPreferencesDataSource
) : UserRepository {
    override suspend fun login(userName: String, password: String): ResultWrapper<AuthToken> {
        val request = LoginRequest(userName = userName, password = password)
        val result = safeApiCall { authApi.login(request).toDomain() }

        if (result is ResultWrapper.Success) {
            preferencesDataSource.saveAuthToken(result.data)
        }

        return result
    }

    override fun observeAuthToken(): Flow<AuthToken?> {
        return preferencesDataSource.authTokenFlow
    }

    override fun isLoggedIn(): Flow<Boolean> {
        return preferencesDataSource.authTokenFlow.map { it != null }
    }
}
