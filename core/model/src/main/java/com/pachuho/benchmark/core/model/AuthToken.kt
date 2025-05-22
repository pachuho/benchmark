package com.pachuho.benchmark.core.model

data class AuthToken(
    val accessToken: String,
    val refreshToken: String
)