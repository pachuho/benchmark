package com.pachuho.benchmark.core.data.api.model.request

import com.pachuho.benchmark.core.model.device.Device
import com.pachuho.benchmark.core.model.device.Light
import com.pachuho.benchmark.core.model.device.Plug
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonElement
import kotlinx.serialization.json.JsonPrimitive

@Serializable
data class DeviceControlRequest(
    @SerialName("code") val code: String,
    @SerialName("value") val value: JsonElement
)

fun Device.extractControlRequest(code: String, value: Any): DeviceControlRequest? {
    val statusObj: Any = when (this) {
        is Plug -> this.status
        is Light -> this.status
        else -> return null
    }

    val kClass = statusObj::class
    val property = kClass.members
        .firstOrNull { it.name == code }
        ?: return null
    val fieldValue = property.call(statusObj)

    val jsonValue: JsonElement = when (value) {
        is Boolean -> JsonPrimitive(value)
        is Int -> JsonPrimitive(value)
        is String -> JsonPrimitive(value)
        else -> return null
    }

    return DeviceControlRequest(
        code = code,
        value = jsonValue
    )
}