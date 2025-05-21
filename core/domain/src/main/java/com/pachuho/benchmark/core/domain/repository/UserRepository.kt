package com.pachuho.benchmark.core.domain.repository

import com.pachuho.benchmark.core.model.ResultWrapper
import com.pachuho.benchmark.core.domain.model.AuthToken

interface UserRepository {
    suspend fun login(userName: String, password: String): ResultWrapper<AuthToken>
}