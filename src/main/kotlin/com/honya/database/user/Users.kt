package com.honya.database.user

import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.transactions.transaction

object Users : Table("users") {
    private val login = Users.varchar(name = "login", length = 30)
    private val email = Users.varchar(name = "email", length = 50)
    private val password = Users.varchar(name = "password", length = 50)


    fun insert(userDTO: UserDTO){
        transaction {
            Users.insert{
                it[login] = userDTO.login
                it[password] = userDTO.password
                it[email] = userDTO.email
            }
        }
    }

    fun fetchUser(userLogin: String): UserDTO?{
        println(userLogin)
        return try {
            transaction {

                val result = Users
                    .select { Users.login.eq(userLogin) }
                    .singleOrNull()

                val userModel = result?.let {
                    UserDTO(
                        login = it[login],
                        email = it[email],
                        password = it[password]
                    )
                }

                userModel
            }

        } catch (e: Exception) {
            e.printStackTrace()
            null
        }

    }
}