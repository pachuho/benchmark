package com.pachuho.benchmark.core.model.device

import com.pachuho.benchmark.core.model.R
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonObject
import kotlinx.serialization.json.boolean
import kotlinx.serialization.json.jsonPrimitive

@Serializable
data class BasicDevice(
    override val name: String,
    override val icon: Int = R.drawable.ic_plug,
    override val deviceId: String,
    override val productId: String,
    override val online: Boolean,
    override val status: BasicStatus
) : Device {
    override fun getDirectControlStatus() = this.status.switch.value
    override fun getControlText() = if (this.status.switch.value) "기기가 켜져있습니다." else "기기가 꺼져있습니다."
}

@Serializable
data class BasicStatus(
    val switch: ControlField<Boolean>
) {
    companion object {
        fun fromJsonObj(obj: JsonObject): BasicStatus {
            val value = obj["switch"]?.jsonPrimitive?.boolean ?: false
            return BasicStatus(switch = ControlField("switch", value))
        }
    }
}