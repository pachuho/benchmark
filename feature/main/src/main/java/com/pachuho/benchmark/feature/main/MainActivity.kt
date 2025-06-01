package com.pachuho.benchmark.feature.main

import android.app.Activity
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.pachuho.benchmark.core.designsystem.theme.BenchmarkTheme
import com.pachuho.benchmark.core.domain.error.BenchmarkException
import com.pachuho.benchmark.core.domain.error.ErrorMapper
import com.pachuho.benchmark.core.eventbus.EventBus
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @Inject
    lateinit var eventBus: EventBus
    private val viewModel: MainViewModel by viewModels()

    override fun onResume() {
        super.onResume()
        viewModel.startWebSocket()
    }

    override fun onStop() {
        super.onStop()
        viewModel.stopWebSocket()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            val viewModel: MainViewModel = hiltViewModel()

            val navigator: MainNavigator = rememberMainNavigator()
            val coroutineScope = rememberCoroutineScope()
            val localContextResource = LocalContext.current.resources
            val snackBarHostState = remember { SnackbarHostState() }
            val popupState = remember { mutableStateOf<EventBus.Event.Popup?>(null) }
            val onShowErrorSnackBar: (throwable: Throwable) -> Unit = { throwable ->

                coroutineScope.launch {
                    snackBarHostState.showSnackbar(
                        localContextResource.getString(ErrorMapper.fromThrowable(throwable))
                    )
                }
            }

            // TODO Remove
            val messages = viewModel.socketMessage.collectAsStateWithLifecycle("", this)

            LaunchedEffect(messages) {
                // messages가 바뀔 때마다 이 블록이 재실행
                Timber.e("message: $messages")
            }

            LaunchedEffect(Unit) {
                eventBus.eventFlow.collect { event ->
                    when(event) {
                        is EventBus.Event.Logout -> navigator.navigateLogin(true)
                        is EventBus.Event.Popup -> popupState.value = event
                        is EventBus.Event.Message -> onShowErrorSnackBar(BenchmarkException(event.messageRes))
                    }
                }
            }

            BenchmarkTheme {
                BackOnPressed(snackBarHostState, coroutineScope)

                popupState.value?.let {
                    AlertDialog(
                        onDismissRequest = { popupState.value = null },
                        title = { Text(popupState.value?.title ?: "") },
                        text = { Text(popupState.value?.body ?: "") },
                        confirmButton = {
                            TextButton(onClick = { popupState.value = null }) {
                                Text("확인")
                            }
                        }
                    )
                }

                var permissionGranted by remember { mutableStateOf(false) }

                NotificationPermissionRequest { granted ->
                    permissionGranted = granted
                }

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