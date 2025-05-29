package com.pachuho.benchmark.core.data.mapper

import com.pachuho.benchmark.core.data.api.model.response.DeviceResponse
import com.pachuho.benchmark.core.model.Device
import com.pachuho.benchmark.core.model.Status

internal fun List<DeviceResponse>.toDomain(): List<Device> {
    return map { response ->
        Device(
            deviceId = response.deviceId,
            productId = response.productId,
            online = response.online,
            status = Status(switch = response.status.switch)
        )
    }
}

internal fun DeviceResponse.toDomain(): Device {
    return Device(
        deviceId = deviceId,
        productId = productId,
        online = online,
        status = Status(switch = status.switch)
    )
}