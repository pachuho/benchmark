package com.pachuho.benchmark.core.data.api.model.request

import com.pachuho.benchmark.core.model.device.ControlField
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonElement
import kotlinx.serialization.json.JsonPrimitive

@Serializable
data class DeviceControlRequest(
    @SerialName("code") val code: String,
    @SerialName("value") val value: JsonElement
)

fun <T> extractControlRequest(field: ControlField<T>): DeviceControlRequest {
    val jsonValue = when (val v = field.value) {
        is Boolean -> JsonPrimitive(v)
        is Int -> JsonPrimitive(v)
        is String -> JsonPrimitive(v)
        else -> throw IllegalArgumentException("지원하지 않는 타입")
    }
    return DeviceControlRequest(field.code, jsonValue)
}