package com.pachuho.benchmark.core.data.impl

import com.pachuho.benchmark.core.data.api.JHApi
import com.pachuho.benchmark.core.data.api.model.request.extractControlRequest
import com.pachuho.benchmark.core.data.mapper.toDomain
import com.pachuho.benchmark.core.domain.repository.DeviceRepository
import com.pachuho.benchmark.core.model.device.ControlField
import com.pachuho.benchmark.core.model.device.Device
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

internal class DeviceRepositoryImpl @Inject constructor(
    private val api: JHApi,
) : DeviceRepository {
    override fun getDevices(): Flow<List<Device>> = flow {
        emit(api.getDevices().toDomain())
    }

    override fun getDevice(deviceId: String): Flow<Device?> = flow {
        emit(api.getDevice(deviceId).toDomain())
    }

    override fun<T> controlDevice(deviceId: String, field: ControlField<T>): Flow<Unit> = flow {
        val response = api.controlDevice(
            deviceId = deviceId,
            request = listOf(extractControlRequest(field))
        )
        emit(response)
    }
}