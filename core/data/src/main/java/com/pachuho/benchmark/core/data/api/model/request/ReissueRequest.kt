package com.pachuho.benchmark.core.data.api.model.request

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
internal data class ReissueRequest(
    @SerialName("refreshToken") val refreshToken: String,
)