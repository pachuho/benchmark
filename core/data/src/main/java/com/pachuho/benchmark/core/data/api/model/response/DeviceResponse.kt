package com.pachuho.benchmark.core.data.api.model.response

import com.pachuho.benchmark.core.model.Device
import com.pachuho.benchmark.core.model.Status
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class DevicesResponse(
    @SerialName("devices") val devices: List<Device>
)