package com.pachuho.benchmark.feature.main

import android.app.Activity
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import com.pachuho.benchmark.core.designsystem.theme.BenchmarkTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import java.net.UnknownHostException

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            val navigator: MainNavigator = rememberMainNavigator()
            val coroutineScope = rememberCoroutineScope()
            val localContextResource = LocalContext.current.resources
            val snackBarHostState = remember { SnackbarHostState() }
            val onShowErrorSnackBar: (throwable: Throwable?) -> Unit = { throwable ->
                coroutineScope.launch {
                    snackBarHostState.showSnackbar(
                        when (throwable) {
                            is UnknownHostException -> localContextResource.getString(R.string.error_message_network)
                            else -> {
                                throwable?.message?.let { message ->
                                    message.ifBlank { null }
                                } ?: run {
                                    localContextResource.getString(R.string.error_message_unknown)
                                }
                            }
                        }
                    )
                }
            }

            BenchmarkTheme {
                BackOnPressed(snackBarHostState, coroutineScope)

                Scaffold(modifier = Modifier,
                    content = { padding ->
                        MainNavHost(
                            navigator = navigator,
                            padding = padding,
                            onShowErrorSnackBar = onShowErrorSnackBar
                        )
                    },
                    snackbarHost = { SnackbarHost(snackBarHostState) }
                )
            }
        }
    }

    @Composable
    fun BackOnPressed(
        snackBarHostState: SnackbarHostState,
        scope: CoroutineScope
    ) {
        val context = LocalContext.current
        var backPressedState by remember { mutableStateOf(true) }
        var backPressedTime = 0L

        BackHandler(enabled = backPressedState) {
            if(System.currentTimeMillis() - backPressedTime <= 1_000) {
                (context as Activity).finish()
            } else {
                backPressedState = true

                scope.launch {
                    snackBarHostState.showSnackbar("한번 더 누르면 앱이 종료됩니다.")
                }
            }
            backPressedTime = System.currentTimeMillis()
        }
    }
}