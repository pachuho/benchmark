package com.pachuho.benchmark.core.model.device

import com.pachuho.benchmark.core.model.R
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonObject
import kotlinx.serialization.json.boolean
import kotlinx.serialization.json.jsonPrimitive

@Serializable
data class PlugDevice(
    override val name: String,
    override val icon: Int = R.drawable.ic_plug,
    override val deviceId: String,
    override val productId: String,
    override val online: Boolean,
    override val status: PlugStatus
) : Device {
    override fun getDirectControlStatus() = this.status.switch.value
    override fun getControlText() = if (this.status.switch.value) "플러그가 켜져있습니다." else "플러그가 꺼져있습니다."
}

@Serializable
data class PlugStatus(
    val switch: ControlField<Boolean>
) {
    companion object {
        fun fromJsonObj(obj: JsonObject): PlugStatus {
            val value = obj["switch"]?.jsonPrimitive?.boolean ?: false
            return PlugStatus(switch = ControlField("switch", value))
        }
    }
}