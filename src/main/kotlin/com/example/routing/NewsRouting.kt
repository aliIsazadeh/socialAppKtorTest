package com.example.routing

import com.example.model.AuthResponse
import com.example.model.SignUpParams
import com.example.repository.news.NewsRepository
import com.example.repository.user.UserRepository
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.koin.ktor.ext.inject

fun Routing.newsRouting(){



    val repository by inject<NewsRepository>()

    route(path = "/news") {

        get {

            val result = repository.getAllNews()
            call.respond(
                status = result.code,
                message = result.data
            )
        }

    }
}