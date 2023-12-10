package com.honya.database.tokens

import ch.qos.logback.core.subst.Token
import com.honya.database.users.Users
import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.transactions.transaction
import kotlin.math.log

object Tokens : Table("tokens") {
    private val token = Tokens.varchar(name = "token", length = 70)
    private val login = Tokens.varchar(name = "login", length = 30)

    fun insert(tokenDTO: TokenDTO) {
        transaction {
            Tokens.insert {
                it[token] = tokenDTO.token
                it[login] = tokenDTO.login
            }
        }
    }

    fun fetchLogin(userToken: String) : TokenDTO? {
        return try {
            transaction {

                val result = Tokens
                    .select {token.eq(userToken)}
                    .singleOrNull()

                val tokenModel = result?.let {
                    TokenDTO(
                        token = it[token],
                        login = it[login]
                    )
                }

                tokenModel
            }

        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }
}