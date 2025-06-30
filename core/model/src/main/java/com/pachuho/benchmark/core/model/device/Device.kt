package com.pachuho.benchmark.core.model.device

import androidx.annotation.DrawableRes
import kotlinx.serialization.KSerializer
import kotlinx.serialization.Serializable
import kotlinx.serialization.descriptors.PrimitiveKind
import kotlinx.serialization.descriptors.PrimitiveSerialDescriptor
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder

interface Device {
    val name: String
    @get:DrawableRes val icon: Int
    val deviceId: String
    val productType: ProductType
    val productId: String
    val online: Boolean
    val status: Any

    fun getDirectControlStatus(): Boolean
    fun getControlText(): String
}

@Serializable(with = ProductTypeSerializer::class)
enum class ProductType {
    BLUNT,
    CAMERA,
    PLUG,
    BASIC
}

object ProductTypeSerializer : KSerializer<ProductType> {
    override val descriptor: SerialDescriptor =
        PrimitiveSerialDescriptor("ProductType", PrimitiveKind.STRING)

    override fun deserialize(decoder: Decoder): ProductType {
        val value = decoder.decodeString()
        return ProductType.entries.find { it.name == value } ?: ProductType.BASIC
    }

    override fun serialize(encoder: Encoder, value: ProductType) {
        encoder.encodeString(value.name)
    }
}

@Serializable
data class ControlField<T>(
    val code: String,
    val value: T
)