package com.pachuho.benchmark.core.domain.socket

import com.pachuho.benchmark.core.model.device.SocketMessage
import kotlinx.coroutines.flow.Flow

interface BenchmarkWebSocket {
    fun connect(url: String)
    fun disconnect()
    val messages: Flow<SocketMessage>
}