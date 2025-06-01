package com.pachuho.benchmark.core.domain.socket

import kotlinx.coroutines.flow.Flow

interface BenchmarkWebSocket {
    fun connect(url: String)
    fun disconnect()
    val messages: Flow<String>
}