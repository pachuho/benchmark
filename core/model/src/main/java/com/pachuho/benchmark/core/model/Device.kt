package com.pachuho.benchmark.core.model

data class Device(
    val deviceId: String,
    val productId: String,
    val online: Boolean,
    val status: Status
)

data class Status(
    val switch: Boolean
)