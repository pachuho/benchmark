package com.pachuho.benchmark.core.domain.repository

import com.pachuho.benchmark.core.model.Device
import com.pachuho.benchmark.core.model.ResultWrapper
import kotlinx.coroutines.flow.Flow

interface DeviceRepository {
    fun getDevice(): Flow<ResultWrapper<List<Device>>>
    fun controlDevice(device: Device): Flow<ResultWrapper<Unit>>
}