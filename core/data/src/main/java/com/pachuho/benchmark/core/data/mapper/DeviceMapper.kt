package com.pachuho.benchmark.core.data.mapper

import com.pachuho.benchmark.core.data.api.model.response.DeviceResponse
import com.pachuho.benchmark.core.model.device.BasicDevice
import com.pachuho.benchmark.core.model.device.BasicStatus
import com.pachuho.benchmark.core.model.device.CameraDevice
import com.pachuho.benchmark.core.model.device.CameraStatus
import com.pachuho.benchmark.core.model.device.Device
import com.pachuho.benchmark.core.model.device.LightDevice
import com.pachuho.benchmark.core.model.device.LightStatus
import com.pachuho.benchmark.core.model.device.PlugDevice
import com.pachuho.benchmark.core.model.device.PlugStatus
import com.pachuho.benchmark.core.model.device.ProductType

internal fun DeviceResponse.toDomain(): Device {
    return when (productType) {
        ProductType.PLUG -> PlugDevice(
            name = name,
            deviceId = deviceId,
            productId = productId,
            online = online,
            status = PlugStatus.fromJsonObj(status)
        )

        ProductType.BLUNT -> LightDevice(
            name = name,
            deviceId = deviceId,
            productId = productId,
            online = online,
            status = LightStatus.fromJsonObj(status)
        )

        ProductType.CAMERA -> CameraDevice(
            name = name,
            deviceId = deviceId,
            productId = productId,
            online = online,
            status = CameraStatus.fromJsonObj(status)
        )

        ProductType.BASIC -> BasicDevice(
            name = name,
            deviceId = deviceId,
            productId = productId,
            online = online,
            status = BasicStatus.fromJsonObj(status)
        )
    }
}

internal fun List<DeviceResponse>.toDomain(): List<Device> = mapNotNull { it.toDomain() }