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
    override fun getControlText() =
        if (this.status.indicator.value) "카메라 상태등이 켜져있습니다." else "카메라 상태등이 꺼져있습니다."

    fun reverseIndicator(): ControlField<Boolean> {
        return ControlField(
            code = this.status.indicator.code,
            value = !this.status.indicator.value
        )
    }

    fun reversePrivateMode(): ControlField<Boolean> {
        return ControlField(
            code = this.status.privateMode.code,
            value = !this.status.privateMode.value
        )
    }

    fun reverseMotionDetect(): ControlField<Boolean> {
        return ControlField(
            code = this.status.motionDetect.code,
            value = !this.status.motionDetect.value
        )
    }
}

@Serializable
data class CameraStatus(
    val indicator: ControlField<Boolean>,
    val privateMode: ControlField<Boolean>,
    val motionDetect: ControlField<Boolean>,
) {
    fun update(controlField: ControlField<JsonObject>): CameraStatus {
        return when (controlField.code) {
            CameraStatusType.Indicator.code -> this.copy(
                indicator = ControlField(
                    code = CameraStatusType.Indicator.code,
                    value = controlField.value[LightStatusType.Switch.code]?.jsonPrimitive?.boolean ?: indicator.value
                )
            )
            CameraStatusType.PrivateMode.code -> this.copy(
                privateMode = ControlField(
                    code = CameraStatusType.PrivateMode.code,
                    value = controlField.value[CameraStatusType.PrivateMode.code]?.jsonPrimitive?.boolean ?: privateMode.value
                )
            )
            CameraStatusType.MotionDetect.code -> this.copy(
                motionDetect = ControlField(
                    code = CameraStatusType.MotionDetect.code,
                    value = controlField.value[CameraStatusType.MotionDetect.code]?.jsonPrimitive?.boolean ?: motionDetect.value
                )
            )
            else -> this
        }
    }

    companion object {
        fun fromJsonObj(obj: JsonObject): CameraStatus {
            return CameraStatus(
                indicator = ControlField(
                    CameraStatusType.Indicator.code,
                    obj[CameraStatusType.Indicator.code]?.jsonPrimitive?.boolean ?: false
                ),
                privateMode = ControlField(
                    CameraStatusType.PrivateMode.code,
                    obj[CameraStatusType.PrivateMode.code]?.jsonPrimitive?.boolean ?: false
                ),
                motionDetect = ControlField(
                    CameraStatusType.MotionDetect.code,
                    obj[CameraStatusType.MotionDetect.code]?.jsonPrimitive?.boolean ?: false
                ),
            )
        }
    }
}

enum class CameraStatusType(
    val code: String
) {
    Indicator("indicator"),
    PrivateMode("privateMode"),
    MotionDetect("motionDetect");
}