package com.pachuho.benchmark.core.model.device

import com.pachuho.benchmark.core.model.R
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonObject
import kotlinx.serialization.json.boolean
import kotlinx.serialization.json.int
import kotlinx.serialization.json.jsonPrimitive

@Serializable
data class Light(
    override val name: String,
    override val icon: Int = R.drawable.ic_light,
    override val deviceId: String,
    override val productId: String,
    override val online: Boolean,
    override val status: LightStatus
) : Device {
    override fun getDirectControlStatus() = this.status.switch.value
    override fun getControlText() = if (this.status.switch.value) "무드등이 켜져있습니다." else "무드등이 꺼져있습니다."
}

@Serializable
data class LightStatus(
    val switch: ControlField<Boolean>,
    val bright: ControlField<Int>,
    val mode: ControlField<String>
) {
    companion object {
        fun fromJsonObj(obj: JsonObject): LightStatus {
            return LightStatus(
                switch = ControlField("switch", obj["switch"]?.jsonPrimitive?.boolean ?: false),
                bright = ControlField("bright", obj["bright"]?.jsonPrimitive?.int ?: 0),
                mode = ControlField("mode", obj["mode"]?.jsonPrimitive?.content ?: "white")
            )
        }
    }
}