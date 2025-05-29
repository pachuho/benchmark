package com.pachuho.benchmark.feature.device

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pachuho.benchmark.core.domain.error.BenchmarkException
import com.pachuho.benchmark.core.domain.error.ErrorConstants
import com.pachuho.benchmark.core.domain.repository.DeviceRepository
import com.pachuho.benchmark.core.model.Device
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
import javax.inject.Inject

@HiltViewModel
class DeviceViewModel @Inject constructor(
    private val deviceRepository: DeviceRepository
) : ViewModel() {
    private val _errorFlow = MutableSharedFlow<Throwable>()
    val errorFlow = _errorFlow.asSharedFlow()

    private val _uiState = MutableStateFlow<DeviceUiState>(DeviceUiState.Devices(emptyList()))
    val uiState = _uiState.asStateFlow()

    private val _isRefreshing = MutableStateFlow(false)
    val isRefreshing = _isRefreshing.asStateFlow()

    init {
        getDevices()
    }

    fun getDevices() {
        deviceRepository.getDevices()
            .onStart { _isRefreshing.value = true }
            .onCompletion { _isRefreshing.value = false }
            .onEach { _uiState.value = DeviceUiState.Devices(it) }
            .catch {
                _uiState.value = DeviceUiState.Error
                _errorFlow.emit(it)
            }
            .launchIn(viewModelScope)
    }

    fun toggleDevice(targetDevice: Device) {
        updateDevice(targetDevice)?.let { device ->
            deviceRepository.controlDevice(device)
                .catch {
                    _errorFlow.emit(it)
                    updateDevice(device)
                }
                .launchIn(viewModelScope)
        } ?: run {
            _errorFlow.tryEmit(BenchmarkException(ErrorConstants.INVALID_DEVICE))
        }
    }

    private fun updateDevice(targetDevice: Device): Device? {
        return (_uiState.value as? DeviceUiState.Devices)?.let { state ->
            val targetDeviceId = targetDevice.deviceId
            val updatedDevices = state.devices.map { device ->
                if (device.deviceId == targetDeviceId) {
                    device.copy(status = device.status.copy(switch = !device.status.switch))
                } else device
            }
            _uiState.value = DeviceUiState.Devices(updatedDevices)
            updatedDevices.find { it.deviceId == targetDeviceId }
        }
    }
}