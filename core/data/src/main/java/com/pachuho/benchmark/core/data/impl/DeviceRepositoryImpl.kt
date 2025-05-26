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
): DeviceRepository {
    override fun getDevice(): Flow<List<Device>> = flow {
        val response = api.getDevices().toDomain()
        emit(response)
    }

}