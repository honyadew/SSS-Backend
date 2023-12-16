package com.honya.feature.token

import kotlinx.serialization.Serializable

@Serializable
data class TokenReceiveRemote (
    val token : String
)

@Serializable
data class TokenResponseRemote (
    val login: String,
    val email: String,
    val firstName: String,
    val lastName: String
)