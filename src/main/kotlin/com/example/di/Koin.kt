package com.example.di

import io.ktor.server.application.*
import io.ktor.server.application.*
import org.koin.dsl.module
import org.koin.ktor.plugin.Koin

fun Application.configureDI(){
    install(Koin){
        modules(appModule)
    }
}