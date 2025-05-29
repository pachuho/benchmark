package com.pachuho.benchmark.core.navigation

import kotlinx.serialization.Serializable

sealed interface Route {
    val route: String

    @Serializable
    data object Login : Route {
        override val route: String get() = ROUTE
        private const val ROUTE = "login"
    }
    @Serializable
    data object Device : Route {
        override val route: String get() = ROUTE
        private const val ROUTE = "device"
    }
    @Serializable
    data class DeviceDetail(val deviceId: String) : Route {
        override val route: String get() = ROUTE
        companion object {
            const val ROUTE = "device_detail/{deviceId}"
        }
    }
}
