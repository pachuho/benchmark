package com.pachuho.benchmark.core.model.device

import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json

@Serializable
data class SocketMessage<T>(
    val deviceId: String,
    val controlField: ControlField<T>
)

internal fun <T> String.toControlElement(): SocketMessage<*> {
    return Json.decodeFromString<SocketMessage<*>>(this)
}