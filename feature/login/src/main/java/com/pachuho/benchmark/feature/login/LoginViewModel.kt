package com.pachuho.benchmark.feature.login

import androidx.compose.runtime.Immutable
import androidx.compose.runtime.Stable
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pachuho.benchmark.core.domain.repository.UserRepository
import com.pachuho.benchmark.core.model.ResultWrapper
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val userRepository: UserRepository
) : ViewModel() {
    private val _errorFlow = MutableSharedFlow<Int>()
    val errorFlow get() = _errorFlow.asSharedFlow()

    private val _uiState = MutableStateFlow<LoginUiState>(LoginUiState.Idle)
    val uiState get() = _uiState.asStateFlow()

    fun login(userName: String, password: String) = viewModelScope.launch {
        _uiState.value = LoginUiState.Loading
        userRepository.login(userName, password).let { result ->
            _uiState.value = LoginUiState.Idle
            when(result) {
                is ResultWrapper.Error -> {
                    _errorFlow.emit(result.messageRes)
                }
                is ResultWrapper.Success -> {
                    userRepository.uploadFirebaseToken()
                    _uiState.value = LoginUiState.Success
                }
            }

        }
    }

}

@Stable
sealed interface LoginUiState {
    @Immutable
    data object Loading : LoginUiState

    @Immutable
    data object Idle : LoginUiState

    @Immutable
    data object Success : LoginUiState
}