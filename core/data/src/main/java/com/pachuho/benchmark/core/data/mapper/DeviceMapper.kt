package com.pachuho.benchmark.core.data.mapper

import com.pachuho.benchmark.core.data.api.model.response.DeviceResponse
import com.pachuho.benchmark.core.model.device.Device
import com.pachuho.benchmark.core.model.device.Light
import com.pachuho.benchmark.core.model.device.LightStatus
import com.pachuho.benchmark.core.model.device.Plug
import com.pachuho.benchmark.core.model.device.PlugStatus
import com.pachuho.benchmark.core.model.device.StatusType

internal fun DeviceResponse.toDomain(): Device? {
    return when (StatusType.from(productId)) {
        StatusType.Plug -> runCatching {
            Plug(
                name = name,
                deviceId = deviceId,
                productId = productId,
                online = online,
                status = PlugStatus.fromJsonObj(status)
            )
        }.getOrNull()
        StatusType.Light -> runCatching {
            Light(
                name = name,
                deviceId = deviceId,
                productId = productId,
                online = online,
                status = LightStatus.fromJsonObj(status)
            )
        }.getOrNull()
        null -> null
    }
}

internal fun List<DeviceResponse>.toDomain(): List<Device> = mapNotNull { it.toDomain() }