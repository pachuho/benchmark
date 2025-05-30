package com.pachuho.benchmark.core.data.mapper

import com.pachuho.benchmark.core.data.api.model.response.DeviceResponse
import com.pachuho.benchmark.core.model.Device
import com.pachuho.benchmark.core.model.Light
import com.pachuho.benchmark.core.model.LightStatus
import com.pachuho.benchmark.core.model.Plug
import com.pachuho.benchmark.core.model.PlugStatus
import com.pachuho.benchmark.core.model.StatusType
import kotlinx.serialization.json.Json

internal fun DeviceResponse.toDomain(): Device? {
    return when (StatusType.from(productId)) {
        StatusType.Plug -> runCatching {
            Plug(
                deviceId = deviceId,
                productId = productId,
                online = online,
                status = Json.decodeFromJsonElement(PlugStatus.serializer(), status)
            )
        }.getOrNull()
        StatusType.Light -> runCatching {
            Light(
                deviceId = deviceId,
                productId = productId,
                online = online,
                status = Json.decodeFromJsonElement(LightStatus.serializer(), status)
            )
        }.getOrNull()
        null -> null
    }
}

internal fun List<DeviceResponse>.toDomain(): List<Device> = mapNotNull { it.toDomain() }