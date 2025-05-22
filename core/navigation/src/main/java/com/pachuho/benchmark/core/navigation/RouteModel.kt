package com.pachuho.benchmark.core.navigation

import kotlinx.serialization.Serializable

sealed interface Route {
    @Serializable
    data object Login : Route

    @Serializable
    data object Device : Route

    @Serializable
    data class DeviceDetail(val deviceId: String) : Route
}

sealed interface MainTabRoute : Route {
    @Serializable
    data object Home : MainTabRoute

    @Serializable
    data object Setting : MainTabRoute
}
