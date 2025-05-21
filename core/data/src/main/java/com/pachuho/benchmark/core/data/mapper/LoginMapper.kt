package com.pachuho.benchmark.core.data.mapper

import com.pachuho.benchmark.core.data.api.model.response.LoginResponse
import com.pachuho.benchmark.core.domain.model.AuthToken

internal fun LoginResponse.toDomain(): AuthToken {
    return AuthToken(accessToken, refreshToken)
}