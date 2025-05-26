package com.pachuho.benchmark.core.data.api.model.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class DeviceResponse(
    @SerialName("deviceId") val deviceId: String,
    @SerialName("productId") val productId: String,
    @SerialName("online") val online: Boolean,
    @SerialName("status") val status: StatusResponse
)

@Serializable
data class StatusResponse(
    @SerialName("switch_1") val switch: Boolean
)
