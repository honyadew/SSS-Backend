package com.honya.feature.token

import io.ktor.server.application.*
import io.ktor.server.routing.*

fun Application.configureTokenRouting() {
    routing {
        post ( "/token" ) {
            val tokenController = TokenController()
            tokenController.performGetToken(call)
        }
    }
}