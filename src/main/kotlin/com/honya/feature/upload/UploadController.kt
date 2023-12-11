package com.honya.feature.upload

import com.honya.database.photo.Photos
import com.honya.database.product.ProductDTO
import com.honya.database.product.Products
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import java.util.Base64
import java.util.*

class UploadController() {
    suspend fun performUpload(call: ApplicationCall) {
        val receive = call.receive<UploadReceiveRemote>()

        //TODO create constant file with all password & no upload to github
        if (receive.adminPassword == "123") {
            val productId = UUID.randomUUID().toString().substring(0, 20)

            val photoIds = if (receive.photoFile != null) {
                val byteArrayPhotos = receive.photoFile.map { base64String ->
                    Base64.getDecoder().decode(base64String.removePrefix("data:image/png;base64,"))
                }

                byteArrayPhotos.map { byteArray ->
                    Photos.insert(byteArray)
                }
            } else {
                null
            }

            val productDTO = ProductDTO(
                productId = productId,
                name = receive.name,
                type = receive.type,
                photoIds = photoIds,
                description = receive.description
            )

            Products.insert(productDTO)


            if (Products.getProductInfo(productId)?.name == productDTO.name) {
                call.respond(UploadResponseRemote(productId = productId))
            } else {
                call.respond(HttpStatusCode.InternalServerError, "Unexpected error")
            }
        } else {
            call.respond(HttpStatusCode.BadRequest, "Wrong admin password")
        }
    }
}
