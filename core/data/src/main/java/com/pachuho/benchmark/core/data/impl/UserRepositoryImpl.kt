package com.pachuho.benchmark.core.data.impl

import com.pachuho.benchmark.core.data.api.JHApi
import com.pachuho.benchmark.core.data.api.extension.safeApiCall
import com.pachuho.benchmark.core.model.ResultWrapper
import com.pachuho.benchmark.core.data.api.model.request.LoginRequest
import com.pachuho.benchmark.core.data.mapper.toDomain
import com.pachuho.benchmark.core.domain.model.AuthToken
import com.pachuho.benchmark.core.domain.repository.UserRepository
import javax.inject.Inject

internal class UserRepositoryImpl @Inject constructor(
    private val authApi: JHApi,
) : UserRepository {
    override suspend fun login(userName: String, password: String): ResultWrapper<AuthToken> {
        val request = LoginRequest(userName = userName, password = password)
        return safeApiCall {
            authApi.login(request).toDomain()
        }
    }
}
