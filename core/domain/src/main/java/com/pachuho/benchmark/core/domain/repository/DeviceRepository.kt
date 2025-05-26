package com.pachuho.benchmark.core.domain.repository

import com.pachuho.benchmark.core.model.Device
import kotlinx.coroutines.flow.Flow

interface DeviceRepository {
    fun getDevice(): Flow<List<Device>>
}