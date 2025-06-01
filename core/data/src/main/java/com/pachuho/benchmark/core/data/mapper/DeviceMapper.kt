package com.pachuho.benchmark.core.data.mapper

import com.pachuho.benchmark.core.data.api.model.response.DeviceResponse
import com.pachuho.benchmark.core.model.device.BasicDevice
import com.pachuho.benchmark.core.model.device.CameraStatus
import com.pachuho.benchmark.core.model.device.DeviceCamera
import com.pachuho.benchmark.core.model.device.Device
import com.pachuho.benchmark.core.model.device.Light
import com.pachuho.benchmark.core.model.device.LightStatus
import com.pachuho.benchmark.core.model.device.Plug
import com.pachuho.benchmark.core.model.device.PlugStatus
import com.pachuho.benchmark.core.model.device.StatusBasic
import com.pachuho.benchmark.core.model.device.StatusType

internal fun DeviceResponse.toDomain(): Device {
    return when (StatusType.from(productId)) {
        StatusType.Plug -> Plug(
            name = name,
            deviceId = deviceId,
            productId = productId,
            online = online,
            status = PlugStatus.fromJsonObj(status)
        )

        StatusType.Light -> Light(
            name = name,
            deviceId = deviceId,
            productId = productId,
            online = online,
            status = LightStatus.fromJsonObj(status)
        )

        StatusType.Camera -> DeviceCamera(
            name = name,
            deviceId = deviceId,
            productId = productId,
            online = online,
            status = CameraStatus.fromJsonObj(status)
        )

        StatusType.Basic -> BasicDevice(
            name = name,
            deviceId = deviceId,
            productId = productId,
            online = online,
            status = StatusBasic.fromJsonObj(status)
        )
    }
}

internal fun List<DeviceResponse>.toDomain(): List<Device> = mapNotNull { it.toDomain() }