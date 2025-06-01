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
    override val status: StatusBasic
) : Device

@Serializable
data class StatusBasic(
    val switch: ControlField<Boolean>
) {
    companion object {
        fun fromJsonObj(obj: JsonObject): StatusBasic {
            val value = obj["switch"]?.jsonPrimitive?.boolean ?: false
            return StatusBasic(switch = ControlField("switch", value))
        }
    }
}