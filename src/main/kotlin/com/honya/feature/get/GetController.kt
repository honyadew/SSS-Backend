package com.honya.feature.get

import com.honya.database.product.ProductDTO
import com.honya.database.product.ProductType
import com.honya.database.product.Products
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*

class GetController {
    suspend fun performGet(call: ApplicationCall) {
        try {
            val receive = call.receive<GetReceiveRemote>()

            val response = if (receive.category != null && ProductType.fromKey(receive.category) != null) {
                val productType = ProductType.fromKey(receive.category)!!
                Products.getProductByType(productType)
            } else {
                Products.getAllProductInfo()
            }

            call.respond(GetResponseRemote(list = response.map { productFromHisDTO(it) }))

        } catch (e: Exception) {
            call.respond(HttpStatusCode.InternalServerError, "An error occurred: ${e.message}")
        }
    }

    private fun productFromHisDTO(productDTO: ProductDTO): Product{
        return Product(
            productId = productDTO.productId,
            name = productDTO.name,
            type = productDTO.type,
            photoIds = productDTO.photoIds ?: emptyList(),
            description = productDTO.description
        )
    }
}