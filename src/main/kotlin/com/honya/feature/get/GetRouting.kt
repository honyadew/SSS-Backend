package com.honya.feature.get

import io.ktor.server.application.*
import io.ktor.server.routing.*

fun Application.configureGetRouting(){
    routing {
        post ("/get" ) {
            val getController = GetController()
            getController.performGet(call)
        }
    }
}