package com.pachuho.benchmark.core.data.api.model.request

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class DeviceControlRequest(
    @SerialName("code") val code: String,
    @SerialName("value") val value: Boolean
)