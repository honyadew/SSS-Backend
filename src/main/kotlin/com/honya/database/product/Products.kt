package com.honya.database.product

import kotlinx.serialization.Serializable
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.transactions.transaction

object Products : Table("products") {
    private val productId = Products.varchar(name = "product_id", length = 70)
    private val name = Products.varchar(name = "name", length = 70)
    private val type = Products.varchar(name = "type", length = 30)
    private val photoIds = Products.varchar(name = "photo_ids", length = 3000)
    private val description = Products.varchar(name = "description", length = 3000)


    fun insert(productDTO: ProductDTO) {
        val jsonIds : String = Json.encodeToString(productDTO.photoIds)

        transaction {
            Products.insert {
                it[productId] = productDTO.productId
                it[name] = productDTO.name
                it[type] = productDTO.type
                it[photoIds] = jsonIds
                it[description] = productDTO.description
            }
        }
    }

    fun getProductInfo(prodId: String) : ProductDTO? {
        return transaction {
            val result = Products.select(productId.eq(prodId)).singleOrNull()

            val productDTO = result?.let {
                ProductDTO(
                    productId = result[productId],
                    name = result[name],
                    type = result[type],
                    photoIds = Json.decodeFromString(result[photoIds]),
                    description = result[description]
                )
            }

            productDTO
        }
    }

    fun getAllProductInfo() : List<ProductDTO>? {
        return transaction {
            val result = Products.selectAll().toList()

            val list : List<ProductDTO> = result.map {
                ProductDTO(
                    productId = it[productId],
                    name = it[name],
                    type = it[type],
                    photoIds = Json.decodeFromString(it[photoIds]),
                    description = it[description]
                )
            }

            list
        }
    }
}