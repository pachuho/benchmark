package com.pachuho.benchmark.core.data.api.model.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonObject

@Serializable
data class DeviceResponse(
    @SerialName("deviceId") val deviceId: String,
    @SerialName("productId") val productId: String,
    @SerialName("online") val online: Boolean,
    @SerialName("status") val status: JsonObject
)