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

    fun reverseSwitch(): ControlField<Boolean> {
        return ControlField(
            code = this.status.switch.code,
            value = !this.status.switch.value
        )
    }
}

@Serializable
data class BasicStatus(
    val switch: ControlField<Boolean>
) {
    fun update(controlField: ControlField<JsonObject>): BasicStatus {
        return when (controlField.code) {
            BasicStatusType.Switch.code -> this.copy(
                switch = ControlField(
                    code = BasicStatusType.Switch.code,
                    value = controlField.value[BasicStatusType.Switch.code]?.jsonPrimitive?.boolean
                        ?: switch.value
                )
            )

            else -> this
        }
    }

    companion object {
        fun fromJsonObj(obj: JsonObject): BasicStatus {
            val value = obj[BasicStatusType.Switch.code]?.jsonPrimitive?.boolean ?: false
            return BasicStatus(switch = ControlField(BasicStatusType.Switch.code, value))
        }
    }
}

enum class BasicStatusType(
    val code: String
) {
    Switch("switch");
}