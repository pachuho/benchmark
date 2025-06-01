package com.pachuho.benchmark.feature.device

import androidx.compose.runtime.Immutable
import androidx.compose.runtime.Stable
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pachuho.benchmark.core.domain.error.BenchmarkException
import com.pachuho.benchmark.core.domain.error.ErrorConstants
import com.pachuho.benchmark.core.domain.repository.DeviceRepository
import com.pachuho.benchmark.core.model.device.ControlField
import com.pachuho.benchmark.core.model.device.Device
import com.pachuho.benchmark.core.model.device.DeviceCamera
import com.pachuho.benchmark.core.model.device.Light
import com.pachuho.benchmark.core.model.device.Plug
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class DeviceDetailViewModel @Inject constructor(
    private val deviceRepository: DeviceRepository,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _errorFlow = MutableSharedFlow<Throwable>()
    val errorFlow = _errorFlow.asSharedFlow()

    private val deviceId: String? = savedStateHandle.get<String>("deviceId")

    private val _uiState = MutableStateFlow<DeviceDetailUiState>(DeviceDetailUiState.Loading)
    val uiState = _uiState.asStateFlow()

    init {
        deviceId?.let {
            getDevice(deviceId)
        } ?: run {
            _errorFlow.tryEmit(BenchmarkException(ErrorConstants.INVALID_DEVICE))
        }
    }

    private fun getDevice(deviceId: String) {
        deviceRepository.getDevice(deviceId)
            .onEach { _uiState.value = DeviceDetailUiState.Device(it) }
            .catch {
                _uiState.value = DeviceDetailUiState.Error
                _errorFlow.emit(it)
            }
            .launchIn(viewModelScope)
    }

    fun toggleDevice() {
        try {
            (_uiState.value as? DeviceDetailUiState.Device)?.let { state ->
                updateDevice(state.device).let { device ->
                    val field = when(device) {
                        is Plug -> device.status.switch
                        is Light -> device.status.switch
                        is DeviceCamera -> device.status.indicator
                        else -> throw BenchmarkException(ErrorConstants.INVALID_DEVICE)
                    }

                    deviceRepository.controlDevice(device.deviceId, field)
                        .catch {
                            _errorFlow.emit(it)
                            DeviceDetailUiState.Device(updateDevice(device))
                        }
                        .launchIn(viewModelScope)
                }
            }
        } catch (e: Exception) {
            _errorFlow.tryEmit(e)
        }
    }

    private fun updateDevice(device: Device): Device {
        return when(device) {
            is Plug -> {
                device.copy(
                    status = device.status.copy(
                        switch = ControlField(
                            code = device.status.switch.code,
                            value = !device.status.switch.value
                        )
                    )
                )
            }
            is Light -> {
                device.copy(
                    status = device.status.copy(
                        switch = ControlField(
                            code = device.status.switch.code,
                            value = !device.status.switch.value
                        )
                    )
                )
            }
            is DeviceCamera -> {
                device.copy(
                    status = device.status.copy(
                        indicator = ControlField(
                            code = device.status.indicator.code,
                            value = !device.status.indicator.value
                        )
                    )
                )
            }
            else -> throw IllegalArgumentException()
        }.apply {
            _uiState.value = DeviceDetailUiState.Device(this)
        }
    }
}

@Stable
sealed interface DeviceDetailUiState {
    @Immutable
    data object Error : DeviceDetailUiState

    @Immutable
    data object Loading : DeviceDetailUiState

    @Immutable
    data class Device(val device: com.pachuho.benchmark.core.model.device.Device) : DeviceDetailUiState
}