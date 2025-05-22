package com.pachuho.benchmark.feature.login

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import kotlinx.coroutines.flow.collectLatest

@Composable
internal fun LoginRoute(
    padding: PaddingValues,
    onLoginSuccess: () -> Unit,
    onShowErrorSnackBar: (throwable: Throwable?) -> Unit,
    viewModel: LoginViewModel = hiltViewModel()
) {
    LaunchedEffect(true) {
        viewModel.errorFlow.collectLatest { throwable -> onShowErrorSnackBar(throwable) }
    }
}

@Composable
private fun LoginScreen(
    padding: PaddingValues,
    loginUiState: LoginUiState
) {

}

@Preview
@Composable
private fun LoginScreenPreview(
) {
    LoginScreen(
        padding = PaddingValues(),
        loginUiState = LoginUiState.Idle
    )
}