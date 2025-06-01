package com.pachuho.benchmark.feature.device

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pachuho.benchmark.core.domain.repository.DeviceRepository
import com.pachuho.benchmark.core.domain.socket.BenchmarkWebSocket
import com.pachuho.benchmark.core.model.device.BasicDevice
import com.pachuho.benchmark.core.model.device.CameraDevice
import com.pachuho.benchmark.core.model.device.ControlField
import com.pachuho.benchmark.core.model.device.LightDevice
import com.pachuho.benchmark.core.model.device.PlugDevice
import com.pachuho.benchmark.core.model.device.getUpdatedDevice
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class DeviceViewModel @Inject constructor(
    private val deviceRepository: DeviceRepository,
    webSocket: BenchmarkWebSocket
) : ViewModel() {
    private val _errorFlow = MutableSharedFlow<Throwable>()
    val errorFlow = _errorFlow.asSharedFlow()

    private val _uiState = MutableStateFlow<DeviceUiState>(DeviceUiState.Devices(emptyList()))
    val uiState = _uiState.asStateFlow()

    private val _isRefreshing = MutableStateFlow(false)
    val isRefreshing = _isRefreshing.asStateFlow()

    private val socketMessage = webSocket.messages

    init {
        getDevices()
        consumeSocketMessage()
    }

    private fun consumeSocketMessage() = viewModelScope.launch {
        socketMessage.collect { message ->
            (_uiState.value as? DeviceUiState.Devices)?.let { state ->
                Timber.i("message: $message")

                val updatedDevices = state.devices.map { device ->
                    if (device.deviceId == message.deviceId) {
                        when (device) {
                            is PlugDevice -> device.copy(status = device.status.update(message.field))
                            is LightDevice -> device.copy(status = device.status.update(message.field))
                            is CameraDevice -> device.copy(status = device.status.update(message.field))
                            is BasicDevice -> device.copy(status = device.status.update(message.field))
                            else -> device
                        }
                    } else device
                }
                _uiState.value = DeviceUiState.Devices(updatedDevices)
            }
        }
    }

    fun getDevices() {
        deviceRepository.getDevices()
            .onStart { _isRefreshing.value = true }
            .onCompletion { _isRefreshing.value = false }
            .onEach { _uiState.value = DeviceUiState.Devices(it) }
            .catch {
                _isRefreshing.value = false
                _uiState.value = DeviceUiState.Error
                _errorFlow.emit(it)
            }
            .launchIn(viewModelScope)
    }

    fun <T> controlDevice(deviceId: String, controlField: ControlField<T>) {
        fun updateDevices() {
            (_uiState.value as? DeviceUiState.Devices)?.let { state ->
                val updatedDevices = state.devices.map { device ->
                    if (device.deviceId == deviceId) {
                        getUpdatedDevice(device, controlField) ?: device
                    } else device
                }
                _uiState.value = DeviceUiState.Devices(updatedDevices)
            }
        }
        updateDevices()

        deviceRepository.controlDevice(deviceId, controlField)
            .catch {
                _errorFlow.emit(it)
            }
            .launchIn(viewModelScope)
    }
}