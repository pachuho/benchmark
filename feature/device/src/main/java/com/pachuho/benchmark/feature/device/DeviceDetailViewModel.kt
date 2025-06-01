package com.pachuho.benchmark.feature.device

import androidx.compose.runtime.Immutable
import androidx.compose.runtime.Stable
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pachuho.benchmark.core.domain.error.BenchmarkException
import com.pachuho.benchmark.core.domain.error.ErrorConstants
import com.pachuho.benchmark.core.domain.repository.DeviceRepository
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
//        (_uiState.value as? DeviceDetailUiState.Device)?.let { state ->
//            updateDevice(state.device).let { device ->
//                deviceRepository.controlDevice(device)
//                    .catch {
//                        _errorFlow.emit(it)
//                        DeviceDetailUiState.Device(updateDevice(device))
//                    }
//                    .launchIn(viewModelScope)
//            }
//        }
    }

//    private fun updateDevice(device: Device): Device {
//        return device.copy(status = device.status.copy(switch = !device.status.switch)).apply {
//            _uiState.value = DeviceDetailUiState.Device(this)
//        }
//    }
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