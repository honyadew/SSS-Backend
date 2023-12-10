package com.honya.feature.login

import com.honya.database.tokens.TokenDTO
import com.honya.database.tokens.Tokens
import com.honya.database.users.Users
import com.honya.feature.cache.InMemoryCache
import com.honya.feature.cache.TokenCache
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import java.util.*

class LoginController() {

    suspend fun performLogin(call: ApplicationCall) {
        val receive = call.receive<LoginReceiveRemote>()
        val userDTO = Users.fetchUser(receive.login)

        if (userDTO == null){
            call.respond(HttpStatusCode.BadRequest, "Login is never used")
        } else {
            if (userDTO.password == receive.password) {

                val token = UUID.randomUUID().toString()

                Tokens.insert(
                    TokenDTO(
                        login = receive.login,
                        token = token
                    )
                )

                call.respond(LoginResponseRemote(token = token))

            } else {
                call.respond(HttpStatusCode.BadRequest, "Invalid password")
            }
        }
    }
}