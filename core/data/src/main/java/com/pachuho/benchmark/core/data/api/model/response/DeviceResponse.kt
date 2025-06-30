package com.pachuho.benchmark.core.data.api.model.response

import com.pachuho.benchmark.core.model.device.ProductType
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonObject

@Serializable
data class DeviceResponse(
    @SerialName("name") val name: String,
    @SerialName("deviceId") val deviceId: String,
    @SerialName("productId") val productId: String,
    @SerialName("productType") val productType: ProductType,
    @SerialName("online") val online: Boolean,
    @SerialName("status") val status: JsonObject
)