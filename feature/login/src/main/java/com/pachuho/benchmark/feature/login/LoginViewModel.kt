package com.pachuho.benchmark.feature.login

import android.util.Log
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.Stable
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pachuho.benchmark.core.domain.repository.UserRepository
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
    private val _errorFlow = MutableSharedFlow<Throwable>()
    val errorFlow get() = _errorFlow.asSharedFlow()

    private val _uiState = MutableStateFlow<LoginUiState>(LoginUiState.Idle)
    val uiState get() = _uiState.asStateFlow()

    fun login(userName: String, password: String) = viewModelScope.launch {
        val result = userRepository.login(userName, password)
        Log.e("asdf", "login reulst: $result")
    }

}

@Stable
sealed interface LoginUiState {
    @Immutable
    data object Loading : LoginUiState

    @Immutable
    data object Idle : LoginUiState

    @Immutable
    data object Error : LoginUiState
}