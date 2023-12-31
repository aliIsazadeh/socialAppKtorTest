package com.example.plugins

import com.example.routing.authRouting
import com.example.routing.newsRouting
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Application.configureRouting() {
    routing {
        authRouting()
        newsRouting()
    }
}
