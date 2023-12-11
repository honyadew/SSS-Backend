package com.honya.database.cart

import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.VarCharColumnType
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.transactions.transaction
import org.postgresql.jdbc.PgArray

object Carts : Table("carts") {
    private val login = Carts.varchar(name = "login", length = 30)
    private val productIds = Carts.varchar(name = "product_ids", length = 5000)

    fun insert(cartDTO: CartDTO){
        val jsonIds = Json.encodeToString(cartDTO.productIds)
        transaction {
            Carts.insert {
                it[login] = cartDTO.login
                it[productIds] = jsonIds
            }
        }
    }

    fun getProductIds(userLogin: String) : CartDTO?{
        return transaction {
            val result = Carts.select(login.eq(userLogin)).singleOrNull()

            val cartDTO = result?.let {
                CartDTO(
                    login = result[login],
                    productIds = Json.decodeFromString(result[productIds])
                )
            }

            cartDTO
        }
    }
}
