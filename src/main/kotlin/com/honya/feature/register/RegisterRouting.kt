package com.honya.feature.register

import com.honya.feature.cache.InMemoryCache
import com.honya.feature.cache.TokenCache
import com.honya.feature.login.LoginReceiveRemote
import com.honya.feature.register.RegisterReceiveRemote
import com.honya.utils.isValidEmail
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import java.util.UUID

fun Application.configureRegisterRouting() {
    routing {

        post("/register") {
            val registerController = RegisterController()

            registerController.registerNewUser(call)
        }
    }
}