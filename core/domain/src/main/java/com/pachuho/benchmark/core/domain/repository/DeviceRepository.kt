package com.pachuho.benchmark.core.domain.repository

import com.pachuho.benchmark.core.model.Device
import kotlinx.coroutines.flow.Flow

interface DeviceRepository {
    fun getDevices(): Flow<List<Device>>
    fun getDevice(deviceId: String): Flow<Device>
    fun controlDevice(device: Device): Flow<Unit>
}