package com.pachuho.benchmark.core.data.mapper

import com.pachuho.benchmark.core.data.api.model.response.DeviceResponse
import com.pachuho.benchmark.core.model.device.DeviceBasic
import com.pachuho.benchmark.core.model.device.CameraStatus
import com.pachuho.benchmark.core.model.device.CameraDevice
import com.pachuho.benchmark.core.model.device.Device
import com.pachuho.benchmark.core.model.device.LightDevice
import com.pachuho.benchmark.core.model.device.LightStatus
import com.pachuho.benchmark.core.model.device.PlugDevice
import com.pachuho.benchmark.core.model.device.PlugStatus
import com.pachuho.benchmark.core.model.device.BasicStatus
import com.pachuho.benchmark.core.model.device.StatusType

internal fun DeviceResponse.toDomain(): Device {
    return when (StatusType.from(productId)) {
        StatusType.Plug -> PlugDevice(
            name = name,
            deviceId = deviceId,
            productId = productId,
            online = online,
            status = PlugStatus.fromJsonObj(status)
        )

        StatusType.Light -> LightDevice(
            name = name,
            deviceId = deviceId,
            productId = productId,
            online = online,
            status = LightStatus.fromJsonObj(status)
        )

        StatusType.Camera -> CameraDevice(
            name = name,
            deviceId = deviceId,
            productId = productId,
            online = online,
            status = CameraStatus.fromJsonObj(status)
        )

        StatusType.Basic -> DeviceBasic(
            name = name,
            deviceId = deviceId,
            productId = productId,
            online = online,
            status = BasicStatus.fromJsonObj(status)
        )
    }
}

internal fun List<DeviceResponse>.toDomain(): List<Device> = mapNotNull { it.toDomain() }