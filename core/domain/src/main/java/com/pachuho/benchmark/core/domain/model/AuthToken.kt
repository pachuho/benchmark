package com.pachuho.benchmark.core.domain.model

data class AuthToken(
    val accessToken: String,
    val refreshToken: String
)