package com.pachuho.benchmark.core.domain.repository

import com.pachuho.benchmark.core.model.ResultWrapper
import com.pachuho.benchmark.core.model.AuthToken
import kotlinx.coroutines.flow.Flow

interface UserRepository {
    suspend fun login(userName: String, password: String): ResultWrapper<AuthToken>
    fun observeAuthToken(): Flow<AuthToken?>
    fun isLoggedIn(): Flow<Boolean>
}