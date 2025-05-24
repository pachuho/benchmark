package com.pachuho.benchmark.core.data.impl

import com.pachuho.benchmark.core.data.api.JHApi
import com.pachuho.benchmark.core.data.api.extension.safeApiCall
import com.pachuho.benchmark.core.data.api.model.request.FirebaseTokenRequest
import com.pachuho.benchmark.core.data.api.model.request.LoginRequest
import com.pachuho.benchmark.core.data.mapper.toDomain
import com.pachuho.benchmark.core.datastore.datasource.AuthTokenPreferencesDataSource
import com.pachuho.benchmark.core.datastore.datasource.FirebaseTokenPreferencesDataSource
import com.pachuho.benchmark.core.domain.repository.UserRepository
import com.pachuho.benchmark.core.model.AuthToken
import com.pachuho.benchmark.core.model.ResultWrapper
import com.pachuho.benchmark.core.model.User
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.map
import javax.inject.Inject

internal class UserRepositoryImpl @Inject constructor(
    private val api: JHApi,
    private val authTokenDataSource: AuthTokenPreferencesDataSource,
    private val firebaseTokenDataSource: FirebaseTokenPreferencesDataSource
) : UserRepository {
    override suspend fun login(userName: String, password: String): ResultWrapper<AuthToken> {
        val request = LoginRequest(userName = userName, password = password)
        val result = safeApiCall { api.login(request).toDomain() }

        if (result is ResultWrapper.Success) {
            authTokenDataSource.saveAuthToken(result.data)
        }

        return result
    }

    override suspend fun uploadFirebaseToken(): Boolean {
        return firebaseTokenDataSource.firebaseToken.firstOrNull()?.let { token ->
            api.uploadFirebaseToken(FirebaseTokenRequest(token))
            true
        } ?: run {
            false
        }
    }

    override fun isLoggedIn(): Flow<Boolean> {
        return authTokenDataSource.authTokenFlow.map { it != null }
    }

    override suspend fun getUser(): ResultWrapper<User> {
        return safeApiCall { api.getUser().toDomain() }
    }
}
