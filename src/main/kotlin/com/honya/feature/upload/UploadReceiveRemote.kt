package com.honya.feature.upload

import kotlinx.serialization.Serializable

@Serializable
data class UploadReceiveRemote(
    val adminPassword: String,
    val name : String,
    val type : String,
    val description: String,
    val photoFile: List<String>?
)

@Serializable
data class UploadResponseRemote(
    val productId : String
)