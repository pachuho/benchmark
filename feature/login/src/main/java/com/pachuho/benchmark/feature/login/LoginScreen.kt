package com.pachuho.benchmark.feature.login

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.pachuho.benchmark.core.designsystem.component.BenchmarkTopAppBar
import com.pachuho.benchmark.core.designsystem.component.TopAppBarNavigationType
import kotlinx.coroutines.flow.collectLatest

@Composable
internal fun LoginRoute(
    padding: PaddingValues,
    onLoginSuccess: () -> Unit,
    onShowErrorSnackBar: (throwable: Throwable?) -> Unit,
    viewModel: LoginViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    LaunchedEffect(true) {
        viewModel.errorFlow.collectLatest { throwable -> onShowErrorSnackBar(throwable) }
    }

    LoginScreen(
        padding = padding,
        loginUiState = uiState
    )
}

@Composable
private fun LoginScreen(
    padding: PaddingValues,
    loginUiState: LoginUiState
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(padding),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        BenchmarkTopAppBar(
            titleRes = R.string.login,
//            modifier = Modifier.statusBarsPadding(), pixel에서 과도한 여백 발생
            navigationType = TopAppBarNavigationType.None
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun LoginScreenPreview(
) {
    LoginScreen(
        padding = PaddingValues(),
        loginUiState = LoginUiState.Idle
    )
}