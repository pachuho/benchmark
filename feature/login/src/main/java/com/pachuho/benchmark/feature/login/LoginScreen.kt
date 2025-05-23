package com.pachuho.benchmark.feature.login

import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.pachuho.benchmark.core.designsystem.component.BenchmarkButton
import com.pachuho.benchmark.core.designsystem.component.BenchmarkTextField
import com.pachuho.benchmark.core.designsystem.component.BenchmarkTopAppBar
import com.pachuho.benchmark.core.designsystem.component.TextFieldType
import com.pachuho.benchmark.core.designsystem.component.TopAppBarNavigationType
import com.pachuho.benchmark.core.designsystem.theme.BenchmarkTheme
import com.pachuho.benchmark.core.ui.clickableWithoutEffect
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
        loginUiState = uiState,
        onLogin = { id, password ->
            viewModel.login(id, password)
        }
    )
}

@Composable
private fun LoginScreen(
    padding: PaddingValues,
    loginUiState: LoginUiState,
    onLogin: (String, String) -> Unit
) {
    var id by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    val keyboardController = LocalSoftwareKeyboardController.current

    Column(
        modifier = Modifier
            .fillMaxSize()
            .clickableWithoutEffect { keyboardController?.hide() }
            .padding(padding),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        BenchmarkTopAppBar(
            titleRes = R.string.login,
            navigationType = TopAppBarNavigationType.None,
        )

        Column(
            modifier = Modifier
                .padding(20.dp)
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            BenchmarkTextField(
                text = id,
                hintRes = R.string.text_field_id,
                imeAction = ImeAction.Next,
                onValueChange = { id = it }
            )

            BenchmarkTextField(
                text = password,
                hintRes = R.string.text_field_password,
                textType = TextFieldType.Password,
                onValueChange = { password = it },
                onKeyboardDoneAction = {
                    keyboardController?.hide()
                    onLogin(id, password)
                }
            )

            BenchmarkButton(
                textRes = R.string.login
            ) {
                onLogin(id, password)
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun LoginScreenPreview(
) {
    BenchmarkTheme {
        LoginScreen(
            padding = PaddingValues(),
            loginUiState = LoginUiState.Idle,
            onLogin = { id, password ->

            }
        )
    }
}