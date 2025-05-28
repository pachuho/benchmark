package com.pachuho.benchmark.feature.main

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import com.pachuho.benchmark.core.navigation.Route
import com.pachuho.benchmark.feature.device.navigation.deviceNavGraph
import com.pachuho.benchmark.feature.login.navigation.loginNavGraph

@Composable
internal fun MainNavHost(
    modifier: Modifier = Modifier,
    navigator: MainNavigator,
    padding: PaddingValues,
    onShowErrorSnackBar: (message: Int) -> Unit,
) {
    Box(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        NavHost(
            navController = navigator.navController,
            startDestination = navigator.startDestination,
        ) {
            loginNavGraph(
                padding = padding,
                onLoginSuccess = { navigator.navigate(Route.Device, true) },
                onShowErrorSnackBar = onShowErrorSnackBar
            )

            deviceNavGraph(
                padding = padding,
                onShowErrorSnackBar = onShowErrorSnackBar,
                onClickItem = { device ->

                }
            )

            // TODO NavGraph 추가
        }
    }
}
