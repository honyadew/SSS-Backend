package com.honya.feature.token

import com.honya.database.token.Tokens
import com.honya.database.user.Users
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*

class TokenController() {

    suspend fun performGetToken(call : ApplicationCall) {
        val receive = call.receive<TokenReceiveRemote>()
        val tokenDTO = Tokens.fetchLogin(receive.token)

        if(tokenDTO == null){
            call.respond(HttpStatusCode.BadRequest, "Invalid token")
        } else {

            val user = Users.fetchUser(tokenDTO.login)

            if (user == null){
                call.respond(HttpStatusCode.BadRequest, "Invalid token")
            } else {
                call.respond(TokenResponseRemote(
                    login = user.login,
                    email = user.email,
                    firstName = user.firstName?:"",
                    lastName = user.lastName?:""
                ))
            }
        }
    }
}