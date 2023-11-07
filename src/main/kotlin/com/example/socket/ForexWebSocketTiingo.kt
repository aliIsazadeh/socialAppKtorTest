package com.example.socket

import io.ktor.client.*
import io.ktor.client.plugins.websocket.*
import io.ktor.client.request.*
import io.ktor.http.*
import io.ktor.websocket.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext
import kotlinx.coroutines.*
import kotlinx.serialization.json.Json

fun getXauusd() = runBlocking {

    withContext(Dispatchers.IO) {
        val client = HttpClient {
            install(WebSockets) {

            }
        }


        client.webSocket(
            method = HttpMethod.Get,
            host = "api.tiingo.com",
            path = "/fx",
            request = {
                header("Authorization", "Token a0fbf7bd530960365bd5c1ca0caf5b738ba95c5e")
            }
        ) {
            val subscribe = """
            {
                "eventName": "subscribe",
                "authorization": "a0fbf7bd530960365bd5c1ca0caf5b738ba95c5e",
                "eventData": {
                    "thresholdLevel": 5,
                    "tickers": ["xauusd"]
                }
            }
        """.trimIndent()

            send(subscribe)

            while (true) {
                val message = incoming.receive() as? Frame.Text ?: continue
                println(message.readText())
            }
        }
//            while (true) {
//                val message = incoming.receive() as? Frame.Text ?: continue
//                println(message.readText())
//                delay(1000L)
//            }
//        }


    }


}