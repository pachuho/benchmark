package com.pachuho.benchmark.core.data.impl

import com.pachuho.benchmark.core.data.api.JHApi
import com.pachuho.benchmark.core.data.api.model.request.FirebaseTokenRequest
import com.pachuho.benchmark.core.data.api.model.request.LoginRequest
import com.pachuho.benchmark.core.data.mapper.toDomain
import com.pachuho.benchmark.core.datastore.datasource.AuthTokenPreferencesDataSource
import com.pachuho.benchmark.core.datastore.datasource.FirebaseTokenPreferencesDataSource
import com.pachuho.benchmark.core.domain.error.BenchmarkException
import com.pachuho.benchmark.core.domain.error.ErrorConstants
import com.pachuho.benchmark.core.domain.repository.UserRepository
import com.pachuho.benchmark.core.model.User
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

internal class UserRepositoryImpl @Inject constructor(
    private val api: JHApi,
    private val authTokenDataSource: AuthTokenPreferencesDataSource,
    private val firebaseTokenDataSource: FirebaseTokenPreferencesDataSource
) : UserRepository {
    override fun login(userName: String, password: String): Flow<Unit> = flow {
        val request = LoginRequest(userName = userName, password = password)
        val result = api.login(request).toDomain().run {
            authTokenDataSource.saveAuthToken(this)
            uploadFirebaseToken()
        }

        emit(result.first())
    }

    private fun uploadFirebaseToken(): Flow<Unit> = flow {
        val result = firebaseTokenDataSource.firebaseToken.firstOrNull()?.let { token ->
            api.uploadFirebaseToken(FirebaseTokenRequest(token))
        } ?: run {
            throw BenchmarkException(ErrorConstants.FIRE_TOKEN_NOT_FOUND)
        }
        emit(result)
    }

    override fun isLoggedIn(): Flow<Boolean> {
        return authTokenDataSource.authTokenFlow.map { it != null }
    }

    override fun getUser(): Flow<User> = flow {
        emit(api.getUser().toDomain())
    }
}
