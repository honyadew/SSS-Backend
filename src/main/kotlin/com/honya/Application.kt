package com.honya

import com.honya.feature.register.configureRegisterRouting
import com.honya.feature.login.configureLoginRouting
import com.honya.feature.token.configureTokenRouting
import com.honya.plugins.*
import io.ktor.server.application.*
import io.ktor.server.cio.*
import io.ktor.server.engine.*
import org.jetbrains.exposed.sql.Database

fun main() {
    Database.connect(
        url = "jdbc:postgresql://localhost:5432/stickerslayshop", driver = "org.postgresql.Driver",
        password = "5656", user = "postgres"
    )


    embeddedServer(CIO, port = 8080, host = "0.0.0.0", module = Application::module)
        .start(wait = true)
}

fun Application.module() {
    configureRouting()
    configureRegisterRouting()
    configureLoginRouting()
    configureSerialization()
    configureTokenRouting()
}
