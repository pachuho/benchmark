package com.pachuho.benchmark.core.model.device

import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonObject

@Serializable
data class SocketMessage(
    val deviceId: String,
    val field: ControlField<JsonObject>
)