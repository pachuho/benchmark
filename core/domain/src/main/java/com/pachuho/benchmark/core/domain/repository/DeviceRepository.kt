package com.pachuho.benchmark.core.domain.repository

import com.pachuho.benchmark.core.model.ResultWrapper
import com.pachuho.benchmark.core.model.AuthToken
import com.pachuho.benchmark.core.model.Device
import com.pachuho.benchmark.core.model.User
import kotlinx.coroutines.flow.Flow

interface DeviceRepository {
    fun getDevice(): Flow<List<Device>>
}