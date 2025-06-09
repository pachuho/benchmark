package com.pachuho.benchmark.core.navigation

import kotlinx.serialization.Serializable

sealed interface BenchmarkRoute {
    val route: String

    @Serializable
    data object Login : BenchmarkRoute {
        override val route: String get() = ROUTE
        private const val ROUTE = "login"
    }
    @Serializable
    data object Device : BenchmarkRoute {
        override val route: String get() = ROUTE
        private const val ROUTE = "device"
    }
    @Serializable
    data class DeviceDetail(val deviceId: String) : BenchmarkRoute {
        override val route: String get() = ROUTE
        companion object {
            const val ROUTE = "device_detail/{deviceId}"
        }
    }
}
