package com.honya.plugins

import com.honya.feature.token.TokenController
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import kotlinx.serialization.Serializable



fun Application.configureRouting() {
    routing {
        get("/") {
            call.respondText("Hello world!")
        }
        //TODO idk, be better to find another way
        post("/") {
            val tokenController = TokenController()
            tokenController.performGetToken(call)
        }
    }
}
