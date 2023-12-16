package com.honya.feature.register

import com.honya.database.token.TokenDTO
import com.honya.database.token.Tokens
import com.honya.database.user.UserDTO
import com.honya.database.user.Users
import com.honya.utils.isValidEmail
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import java.util.*

class RegisterController(){

    suspend fun registerNewUser(call: ApplicationCall) {

        val receive = call.receive<RegisterReceiveRemote>()
        val userDTO = Users.fetchUser(receive.login)

        if (!receive.email.isValidEmail()) {
            call.respond(HttpStatusCode.BadRequest, "Email is not valid")
        }

        if (userDTO != null){
            call.respond(HttpStatusCode.Conflict, "User already exist")
        } else {
            val token = UUID.randomUUID().toString()
            try {
                Users.insert(
                    UserDTO(
                        login = receive.login,
                        email = receive.email,
                        password = receive.password,
                        firstName = receive.firstName,
                        lastName = receive.lastName,
                        phoneNumber = receive.phoneNumber
                    )
                )

            } catch (e: Exception){
                call.respond(HttpStatusCode.Conflict, "User already exist here")
            }

            Tokens.insert(
                TokenDTO(
                    token = token,
                    login = receive.login
                )
            )

            call.respond(RegisterResponseRemote(token = token))
        }
    }
}