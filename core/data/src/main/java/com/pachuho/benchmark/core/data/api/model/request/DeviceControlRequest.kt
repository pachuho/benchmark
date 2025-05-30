package com.pachuho.benchmark.core.data.api.model.request

import com.pachuho.benchmark.core.model.ControlField
import com.pachuho.benchmark.core.model.Device
import com.pachuho.benchmark.core.model.Light
import com.pachuho.benchmark.core.model.Plug
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
    val controlFieldMap: Map<String, ControlField<*>> = when (this) {
        is Plug -> mapOf(this.status.switch.code to this.status.switch)
        is Light -> mapOf(
            this.status.switchLed.code to this.status.switchLed,
            this.status.brightValue.code to this.status.brightValue,
            this.status.workMode.code to this.status.workMode
        )
        else -> emptyMap()
    }
    val field = controlFieldMap[code] ?: return null

    val jsonValue = when (value) {
        is Boolean -> JsonPrimitive(value)
        is Int -> JsonPrimitive(value)
        is String -> JsonPrimitive(value)
        else -> return null // 필요시 Double, Long 등도 추가
    }

    return DeviceControlRequest(code = code, value = jsonValue)
}