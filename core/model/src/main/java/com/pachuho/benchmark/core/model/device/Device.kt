package com.pachuho.benchmark.core.model.device

import androidx.annotation.DrawableRes
import kotlinx.serialization.Serializable

interface Device {
    val name: String
    @get:DrawableRes val icon: Int
    val deviceId: String
    val productId: String
    val online: Boolean
    val status: Any

    fun getDirectControlStatus(): Boolean
    fun getControlText(): String
}

enum class StatusType(val productId: String) {
    Plug("uxjr57hvapakd0io"),
    Light("mhf0rqd7uuvz6hf8"),
    Camera("3cwbcqiz8qixphvu"),
    Basic("1");

    companion object {
        fun from(productId: String): StatusType =
            entries.find { it.productId == productId } ?: Basic
    }
}

@Serializable
data class ControlField<T>(
    val code: String,
    val value: T
)