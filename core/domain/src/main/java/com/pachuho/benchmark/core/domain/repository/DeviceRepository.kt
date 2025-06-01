package com.pachuho.benchmark.core.domain.repository

import com.pachuho.benchmark.core.model.device.ControlField
import com.pachuho.benchmark.core.model.device.Device
import kotlinx.coroutines.flow.Flow

interface DeviceRepository {
    fun getDevices(): Flow<List<Device>>
    fun getDevice(deviceId: String): Flow<Device>
    fun<T> controlDevice(deviceId: String, field: ControlField<T>): Flow<Unit>
}