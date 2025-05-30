package com.pachuho.benchmark.core.data.impl

import com.pachuho.benchmark.core.data.api.JHApi
import com.pachuho.benchmark.core.data.mapper.toDomain
import com.pachuho.benchmark.core.domain.repository.DeviceRepository
import com.pachuho.benchmark.core.model.Device
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

    override fun controlDevice(device: Device): Flow<Unit> = flow {
//        val response = api.controlDevice(
//            deviceId = device.deviceId,
//            listOf(
//                DeviceControlRequest(
//                    code = "switch_1",
//                    value = device.extractControlRequest()
//                )
//            )
//        )
//
//        emit(response)
    }
}