package com.honya.feature.register

import kotlinx.serialization.Serializable

@Serializable
data class RegisterReceiveRemote(
    val login : String,
    val email: String,
    val password: String,
    val firstName: String? = null,
    val lastName: String? = null,
    val phoneNumber: String? = null
)

@Serializable
data class RegisterResponseRemote (
    val token : String
)
