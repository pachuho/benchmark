package com.pachuho.benchmark.core.data.mapper

import com.pachuho.benchmark.core.data.api.model.response.LoginResponse
import com.pachuho.benchmark.core.data.api.model.response.UserResponse
import com.pachuho.benchmark.core.model.AuthToken
import com.pachuho.benchmark.core.model.User

internal fun UserResponse.toDomain(): User {
    return User(username = username)
}