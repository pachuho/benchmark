package com.pachuho.benchmark.feature.device

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pachuho.benchmark.core.domain.repository.DeviceRepository
import com.pachuho.benchmark.core.model.Device
import com.pachuho.benchmark.core.model.ResultWrapper
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import java.net.UnknownHostException
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
        deviceRepository.getDevice()
            .onStart { _isRefreshing.value = true }
            .map { result ->
                when(result) {
                    is ResultWrapper.Error -> {
                        _uiState.value = DeviceUiState.Error
                        _errorFlow.emit(Throwable(result.message))
                    }
                    ResultWrapper.NetworkError -> {
                        _uiState.value = DeviceUiState.Error
                        _errorFlow.emit(UnknownHostException())
                    }
                    is ResultWrapper.Success -> {
                        _uiState.value = DeviceUiState.Devices(result.data)
                    }
                }
                _isRefreshing.value = false
            }
            .launchIn(viewModelScope)
    }

    fun toggleDevice(target: Device) {
        (_uiState.value as? DeviceUiState.Devices)?.let { state ->
            val targetDeviceId = target.deviceId
            val updatedDevices = state.devices.map { device ->
                if (device.deviceId == targetDeviceId) {
                    device.copy(status = device.status.copy(switch = !device.status.switch))
                } else device
            }
            _uiState.value = DeviceUiState.Devices(updatedDevices)
            updatedDevices.find { it.deviceId == targetDeviceId }?.let {
                controlDevice(it)
            }
        }
    }

    private fun controlDevice(device: Device) {
        deviceRepository.controlDevice(device)
            .debounce(300)
            .map { result ->
                when(result) {
                    is ResultWrapper.Error -> {
                        _errorFlow.emit(Throwable(result.message))
                    }
                    ResultWrapper.NetworkError -> {
                        _errorFlow.emit(UnknownHostException())
                    }
                    is ResultWrapper.Success -> {}
                }
            }
            .launchIn(viewModelScope)
    }
}