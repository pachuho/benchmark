package com.pachuho.benchmark.core.model.device

import com.pachuho.benchmark.core.model.R
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonObject
import kotlinx.serialization.json.boolean
import kotlinx.serialization.json.jsonPrimitive

@Serializable
data class CameraDevice(
    override val name: String,
    override val icon: Int = R.drawable.ic_camera,
    override val deviceId: String,
    override val productId: String,
    override val online: Boolean,
    override val status: CameraStatus
) : Device {
    override fun getDirectControlStatus() = this.status.indicator.value
    override fun getControlText() = if (this.status.indicator.value) "카메라 상태등이 켜져있습니다." else "카메라 상태등이 꺼져있습니다."

    fun updateIndicator(): CameraDevice {
        return this.copy(
            status = this.status.copy(
                indicator = ControlField(
                    code = this.status.indicator.code,
                    value = !this.status.indicator.value
                )
            )
        )
    }

    fun updatePrivateMode(): CameraDevice {
        return this.copy(
            status = this.status.copy(
                indicator = ControlField(
                    code = this.status.privateMode.code,
                    value = !this.status.privateMode.value
                )
            )
        )
    }
}

@Serializable
data class CameraStatus(
    val indicator: ControlField<Boolean>,
    val privateMode: ControlField<Boolean>,
) {
    companion object {
        fun fromJsonObj(obj: JsonObject): CameraStatus {
            return CameraStatus(
                indicator = ControlField("indicator", obj["indicator"]?.jsonPrimitive?.boolean ?: false),
                privateMode = ControlField("privateMode", obj["privateMode"]?.jsonPrimitive?.boolean ?: false),
            )
        }
    }
}