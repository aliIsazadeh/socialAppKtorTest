package com.example

import com.example.dao.DatabaseFactory
import com.example.di.configureDI
import com.example.plugins.*
import com.example.socket.getXauusd
import io.ktor.server.application.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*

fun main() {
    embeddedServer(Netty, port = 8081, host = "0.0.0.0", module = Application::module)
            .start(wait = true)
}

fun Application.module() {
    DatabaseFactory.init()
//    getXauusd()
//    configureSecurity()
    configureSerialization()
    configureDI()
    configureRouting()
}
