package com.pachuho.benchmark.core.model

import kotlinx.serialization.Serializable

@Serializable
data class Device(
    val deviceId: String,
    val projectId: String,
    val online: Boolean,
    val status: Status
)

@Serializable
data class Status(
    val switch: Boolean
)
