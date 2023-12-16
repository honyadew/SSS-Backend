package com.honya.database.user

//Data Transfer Object
class UserDTO (
    val login : String,
    val email : String,
    val password: String,
    val firstName: String?,
    val lastName: String?,
    val phoneNumber: String?
)