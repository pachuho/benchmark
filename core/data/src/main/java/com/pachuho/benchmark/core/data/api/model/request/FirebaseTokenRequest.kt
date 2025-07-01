package com.pachuho.benchmark.core.data.api.model.request

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class FirebaseTokenRequest(
    @SerialName("token") val token: String,
)