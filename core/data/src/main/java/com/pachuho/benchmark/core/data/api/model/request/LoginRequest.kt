package com.pachuho.benchmark.core.data.api.model.request

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class LoginRequest(
    @SerialName("username") val userName: String,
    @SerialName("password") val password: String,
)