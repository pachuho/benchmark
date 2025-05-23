package com.pachuho.benchmark.core.data.interceptor

import android.util.Log
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonElement
import okhttp3.logging.HttpLoggingInterceptor
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class LoggerInterceptor @Inject constructor() : HttpLoggingInterceptor.Logger {
    companion object {
        const val LOG_DIVIDER = "================================================================"

        private val json = Json {
            prettyPrint = true
            prettyPrintIndent = "  "
            isLenient = true
            ignoreUnknownKeys = true
        }
    }

    override fun log(message: String) {
        val trimMessage = message.trim { it <= ' ' }
        if ((trimMessage.startsWith("{") && trimMessage.endsWith("}"))
            || (trimMessage.startsWith("[") && trimMessage.endsWith("]"))
        ) {
            try {
                val jsonElement = json.parseToJsonElement(message)
                val prettyPrintJson = json.encodeToString(JsonElement.serializer(), jsonElement)
                Log.w("Network Response", LOG_DIVIDER + "\n" + prettyPrintJson + "\n" + LOG_DIVIDER)
            } catch (e: Exception) {
                Log.w("Network Response", message, null)
            }
        } else {
            Log.w("Network Response", message, null)
        }
    }
}