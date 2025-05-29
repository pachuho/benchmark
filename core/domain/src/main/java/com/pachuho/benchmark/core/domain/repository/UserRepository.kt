package com.pachuho.benchmark.core.domain.repository

import com.pachuho.benchmark.core.model.User
import kotlinx.coroutines.flow.Flow

interface UserRepository {
    fun isLoggedIn(): Flow<Boolean>
    fun getUser(): Flow<User>
    fun login(userName: String, password: String): Flow<Unit>
}