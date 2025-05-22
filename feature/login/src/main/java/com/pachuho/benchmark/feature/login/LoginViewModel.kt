package com.pachuho.benchmark.feature.login

import androidx.compose.runtime.Immutable
import androidx.compose.runtime.Stable
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pachuho.benchmark.core.domain.repository.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val userRepository: UserRepository
) : ViewModel() {

    private val _errorFlow = MutableSharedFlow<Throwable>()
    val errorFlow get() = _errorFlow.asSharedFlow()

    fun login(userName: String, password: String) = viewModelScope.launch {
        userRepository.login(userName, password)
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