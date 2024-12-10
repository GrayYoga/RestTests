package api

import Config
import dto.WsMessage
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Request.Builder
import okhttp3.Response
import okhttp3.WebSocket
import okhttp3.WebSocketListener
import utils.JacksonMapper

class WsClient {
    private val client = OkHttpClient()
    val listOfWsMessages = mutableListOf<WsMessage>()
    fun start() {
        val request: Request = Builder().url("${Config.baseUrl}/ws").build()
        val listener: WebSocketListener = object : WebSocketListener() {
            override fun onOpen(webSocket: WebSocket, response: Response) {
                println("Connected")
            }

            override fun onMessage(webSocket: WebSocket, text: String) {
                println("Receiving from ws: $text")
                listOfWsMessages.addLast(JacksonMapper.readValue(text, WsMessage::class.java))
            }

            override fun onClosing(webSocket: WebSocket, code: Int, reason: String) {
                webSocket.close(1000, null)
                println("Closing : $code / $reason")
            }

            override fun onFailure(webSocket: WebSocket, t: Throwable, response: Response?) {
                t.printStackTrace()
            }
        }
        client.newWebSocket(request, listener)
        client.dispatcher.executorService.shutdown()
    }
}
