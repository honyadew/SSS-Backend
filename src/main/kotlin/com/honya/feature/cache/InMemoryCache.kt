package com.honya.feature.cache

import com.honya.feature.register.RegisterReceiveRemote
import com.honya.feature.register.RegisterResponseRemote

data class TokenCache (
    val login : String,
    val token: String
)

object InMemoryCache {
    val userList: MutableList<RegisterReceiveRemote > = mutableListOf()
    val token: MutableList<TokenCache> = mutableListOf()
}