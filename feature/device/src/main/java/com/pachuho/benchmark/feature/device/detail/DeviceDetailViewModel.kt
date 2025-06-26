package com.pachuho.benchmark.feature.device.detail

import androidx.compose.runtime.Immutable
import androidx.compose.runtime.Stable
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pachuho.benchmark.core.domain.repository.DeviceRepository
import com.pachuho.benchmark.core.model.device.ControlField
import com.pachuho.benchmark.core.model.device.getUpdatedDevice
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
            _errorFlow.tryEmit(Exception(Throwable("invalid device")))
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

    fun <T> controlDevice(controlField: ControlField<T>) {
        (_uiState.value as? DeviceDetailUiState.Device)?.let { state ->

            val newDevice = getUpdatedDevice(state.device, controlField)

            newDevice?.let {
                _uiState.value = DeviceDetailUiState.Device(it)
                deviceRepository.controlDevice(state.device.deviceId, controlField)
                    .catch {
                        _errorFlow.emit(it)
                        _uiState.value = DeviceDetailUiState.Device(state.device)
                    }
                    .launchIn(viewModelScope)
            } ?: run {
                _uiState.value = DeviceDetailUiState.Device(state.device)
                _errorFlow.tryEmit(Exception(Throwable("invalid device")))
            }
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