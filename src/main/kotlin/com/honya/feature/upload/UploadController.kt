package com.honya.feature.upload

import com.honya.database.photo.Photos
import com.honya.database.product.ProductDTO
import com.honya.database.product.ProductType
import com.honya.database.product.Products
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import java.util.Base64
import java.util.*

class UploadController() {
    suspend fun performUpload(call: ApplicationCall) {
        try {
            val receive = call.receive<UploadReceiveRemote>()

            if (authenticateAdmin(receive.adminPassword)) {
                val productDTO = createProductDTO(receive)

                val productType = ProductType.fromKey(receive.type) ?: ProductType.OTHER

                val insert = ProductDTO(
                    productId = productDTO.productId,
                    name = productDTO.name,
                    type = productType.key,
                    photoIds = productDTO.photoIds,
                    description = productDTO.description
                )

                Products.insert(insert)

                if (Products.getProductInfo(productDTO.productId)?.name == productDTO.name) {
                    call.respond(UploadResponseRemote(productId = productDTO.productId))
                } else {
                    call.respond(HttpStatusCode.InternalServerError, "Unexpected error")
                }
            } else {
                call.respond(HttpStatusCode.BadRequest, "Wrong admin password")
            }
        } catch (e: Exception) {
            call.respond(HttpStatusCode.InternalServerError, "An error occurred: ${e.message}")
        }
    }

    private fun authenticateAdmin(adminPassword: String): Boolean {
        //TODO better secure secure authentication system like OAuth or JWT
        return adminPassword == "123"
    }

    private fun createProductDTO(receive: UploadReceiveRemote): ProductDTO {
        val productId = UUID.randomUUID().toString().substring(0..10)

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

        return ProductDTO(
            productId = productId,
            name = receive.name,
            type = receive.type,
            photoIds = photoIds,
            description = receive.description
        )
    }
}
