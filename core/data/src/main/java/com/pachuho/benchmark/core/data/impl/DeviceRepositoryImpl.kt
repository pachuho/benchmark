package com.pachuho.benchmark.core.data.impl

import com.pachuho.benchmark.core.data.api.JHApi
import com.pachuho.benchmark.core.data.api.extension.safeApiCall
import com.pachuho.benchmark.core.data.api.model.request.DeviceControlRequest
import com.pachuho.benchmark.core.data.mapper.toDomain
import com.pachuho.benchmark.core.domain.repository.DeviceRepository
import com.pachuho.benchmark.core.model.Device
import com.pachuho.benchmark.core.model.ResultWrapper
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

internal class DeviceRepositoryImpl @Inject constructor(
    private val api: JHApi,
) : DeviceRepository {
    override fun getDevice(): Flow<ResultWrapper<List<Device>>> = flow {
        val response = safeApiCall { api.getDevices().toDomain() }
        emit(response)
    }

    override fun controlDevice(device: Device): Flow<ResultWrapper<Unit>> = flow {
        val response = safeApiCall {
            api.controlDevice(
                deviceId = device.deviceId,
                listOf(
                    DeviceControlRequest(
                        code = "switch_1",
                        value = device.status.switch
                    )
                )
            )
        }
        emit(response)
    }
}