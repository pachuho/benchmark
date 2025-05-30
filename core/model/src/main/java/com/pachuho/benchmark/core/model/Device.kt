package com.pachuho.benchmark.core.model

import kotlinx.serialization.Serializable

interface Device {
    val deviceId: String
    val productId: String
    val online: Boolean
    val status: Any

    fun getName(): String {
        return when(this) {
            is Plug -> "플러그 Mini"
            is Light -> "스마트 무드등"
            else -> "Unknown"
        }
    }

    fun getDirectControlStatus(): Boolean {
        return when(this) {
            is Plug -> this.status.switch.value
            is Light -> this.status.switchLed.value
            else -> false
        }
    }
}

@Serializable
data class Plug(
    override val deviceId: String,
    override val productId: String,
    override val online: Boolean,
    override val status: PlugStatus
) : Device

@Serializable
data class Light(
    override val deviceId: String,
    override val productId: String,
    override val online: Boolean,
    override val status: LightStatus
) : Device

@Serializable
data class PlugStatus(
    val switch: ControlField<Boolean> = ControlField("switch_1", false)
)

@Serializable
data class LightStatus(
    val switchLed: ControlField<Boolean> = ControlField("switch_led", false),
    val brightValue: ControlField<Int> = ControlField("bright_value", 0),
    val workMode: ControlField<String> = ControlField("work_mode", "white")
)

@Serializable
data class ControlField<T>(
    val code: String,
    val value: T
)

enum class StatusType(val productId: String) {
    Plug("uxjr57hvapakd0io"),
    Light("mhf0rqd7uuvz6hf8");

    companion object {
        fun from(productId: String): StatusType? =
            entries.find { it.productId == productId }
    }
}