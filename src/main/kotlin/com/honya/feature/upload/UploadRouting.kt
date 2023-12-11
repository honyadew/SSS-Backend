package com.honya.feature.upload

import io.ktor.server.application.*
import io.ktor.server.routing.*

fun Application.configureUploadRouting(){
    routing {
        post("/admin/upload") {
            val uploadController = UploadController()
            uploadController.performUpload(call)
        }
    }
}