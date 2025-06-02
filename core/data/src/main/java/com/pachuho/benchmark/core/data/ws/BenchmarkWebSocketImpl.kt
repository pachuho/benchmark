package com.pachuho.benchmark.core.data.ws

import com.pachuho.benchmark.core.domain.socket.BenchmarkWebSocket
import com.pachuho.benchmark.core.model.device.SocketMessage
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonElement
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import okhttp3.WebSocket
import okhttp3.WebSocketListener
import timber.log.Timber
import javax.inject.Inject

class BenchmarkWebSocketImpl @Inject constructor(
    private val client: OkHttpClient
) : BenchmarkWebSocket {

    private var webSocket: WebSocket? = null
    private val _messageChannel = Channel<SocketMessage>(Channel.BUFFERED)
    override val messages: Flow<SocketMessage> get() = _messageChannel.receiveAsFlow()

    override fun connect(url: String) {
        val request = Request.Builder().url(url).build()
        webSocket = client.newWebSocket(request, object : WebSocketListener() {
            override fun onMessage(webSocket: WebSocket, text: String) {
                Timber.e("onMessage: ${text.prettyJson()}")
//                try {
//                    _messageChannel.trySend(text.toSocketMessage())
//                } catch (e: Exception) {
//                    Timber.e(e)
//                }
            }
            override fun onClosed(webSocket: WebSocket, code: Int, reason: String) {
                _messageChannel.close()
            }
            override fun onFailure(webSocket: WebSocket, t: Throwable, response: Response?) {
                _messageChannel.close(t)
            }
        })
    }
    override fun disconnect() {
        webSocket?.close(1000, null)
        webSocket = null
    }

    private fun String.toSocketMessage(): SocketMessage {
        return Json.decodeFromString<SocketMessage>(this)
    }

    fun String.prettyJson(): String {
        return try {
            val jsonElement: JsonElement = Json.parseToJsonElement(this)
            Json { prettyPrint = true }.encodeToString(JsonElement.serializer(), jsonElement)
        } catch (e: Exception) {
            // 파싱 불가할 경우 원본 문자열 반환 (에러처리 가능)
            this
        }
    }
}