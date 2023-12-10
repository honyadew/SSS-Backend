package com.honya.feature.login

import com.honya.feature.cache.InMemoryCache
import com.honya.feature.cache.TokenCache
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import java.util.UUID

fun Application.configureLoginRouting() {
    routing {
        post("/login") {
            val loginController = LoginController()
             loginController.performLogin(call)
        }
    }
}