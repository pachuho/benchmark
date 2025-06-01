package com.pachuho.benchmark.feature.main

import androidx.lifecycle.ViewModel
import com.pachuho.benchmark.core.data.BuildConfig
import com.pachuho.benchmark.core.domain.socket.BenchmarkWebSocket

import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val webSocket: BenchmarkWebSocket
): ViewModel() {
    val socketMessage = webSocket.messages

    fun startWebSocket() {
        webSocket.connect(BuildConfig.WS_BASE_URL)
    }

    fun stopWebSocket() {
        webSocket.disconnect()
    }
}