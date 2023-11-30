package org.bot.common.util

import io.ktor.client.*
import io.ktor.client.engine.cio.*
import io.ktor.client.plugins.websocket.*
import io.ktor.http.*
import io.ktor.websocket.*
import kotlinx.coroutines.runBlocking
import kotlinx.serialization.json.Json
import java.net.http.WebSocket


/**
 * Websocket
 * @author jhlz
 * @since 1.0
 */

private lateinit var websocket: WebSocket

fun main() {
    val wssUrl = "wss://api.sgroup.qq.com/websocket"
    // println(1 shl 9 or 0)
    val client = HttpClient(CIO) {
        install(WebSockets)
    }

    runBlocking {
        val token = Json.decodeFromString<Map<String, String>>(QQUtil.getAccessToken())
        val msg = """
            {
              "op": 2,
              "d": {
                "token": "QQBot ${token["access_token"]}",
                "intents": 512,
                "shard": [0, 4]
              }
            }
        """.trimIndent()
        val u = Url(wssUrl)
        client.wss(method = HttpMethod.Get, host = u.host, port = u.port, path = u.encodedPath) {
            while (true) {
                val othersMessage = incoming.receive() as? Frame.Text ?: continue
                val message = othersMessage.readText()
                println(message)
                val decodeFromString = Json.decodeFromString<Map<String, Any>>(message)
                if (msg != null) {
                    send(msg)
                }
            }
        }
    }
    client.close()
    println("Connection closed. Goodbye!")
    // val client = OkHttpClient()
    // // .newBuilder().connectionSpecs(arrayListOf(ConnectionSpec.RESTRICTED_TLS)).build()
    // val request = Request.Builder().url("wss://api.sgroup.qq.com/websocket").build()
    // val socket = client.newWebSocket(request, MyListener())
    // websocket = socket
}
