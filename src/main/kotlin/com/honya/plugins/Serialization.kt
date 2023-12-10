package com.honya.plugins

import io.ktor.serialization.kotlinx.json.*
import io.ktor.server.application.*
import io.ktor.server.plugins.contentnegotiation.*
import kotlinx.serialization.Serializer

fun Application.configureSerialization() {
    install(ContentNegotiation) {
        json()
    }
}