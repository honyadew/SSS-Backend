package com.honya.database.product

import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.transactions.transaction

object Products : Table("products") {
    private val productId = Products.varchar(name = "product_id", length = 70)
    private val name = Products.varchar(name = "name", length = 70)
    private val type = Products.varchar(name = "type", length = 30)
    private val photoIds = Products.registerColumn<List<String>?>(
        name = "photo_ids",
        type = VarCharColumnType(70)
    )
    private val description = Products.varchar(name = "description", length = 3000)

    fun insert(productDTO: ProductDTO) {
        transaction {
            Products.insert {
                it[productId] = productDTO.productId
                it[name] = productDTO.name
                it[type] = productDTO.type
                it[photoIds] = productDTO.photoIds
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
                    photoIds = result[photoIds],
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
                    photoIds = it[photoIds],
                    description = it[description]
                )
            }

            list
        }
    }

}