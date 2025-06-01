package com.pachuho.benchmark.feature.device

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pachuho.benchmark.core.domain.error.BenchmarkException
import com.pachuho.benchmark.core.domain.error.ErrorConstants
import com.pachuho.benchmark.core.domain.repository.DeviceRepository
import com.pachuho.benchmark.core.model.device.ControlField
import com.pachuho.benchmark.core.model.device.Device
import com.pachuho.benchmark.core.model.device.LightDevice
import com.pachuho.benchmark.core.model.device.PlugDevice
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
            val field = when(device) {
                is PlugDevice -> device.status.switch
                is LightDevice -> device.status.switch
                else -> throw BenchmarkException(ErrorConstants.INVALID_DEVICE)
            }
            deviceRepository.controlDevice(device.deviceId, field)
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
                    when(device) {
                        is PlugDevice -> {
                            device.copy(
                                status = device.status.copy(
                                    switch = device.reverseSwitch()
                                )
                            )
                        }
                        is LightDevice -> {
                            device.copy(
                                status = device.status.copy(
                                    switch = ControlField(
                                        code = device.status.switch.code,
                                        value = !device.status.switch.value
                                    )
                                )
                            )
                        }
                        else -> throw BenchmarkException(ErrorConstants.INVALID_DEVICE)
                    }
                } else device
            }
            _uiState.value = DeviceUiState.Devices(updatedDevices)
            updatedDevices.find { it.deviceId == targetDeviceId }
        }
    }
}