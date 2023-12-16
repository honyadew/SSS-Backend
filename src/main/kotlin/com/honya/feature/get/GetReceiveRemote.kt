package com.honya.feature.get

import com.honya.database.product.ProductDTO
import com.honya.database.product.Products
import kotlinx.serialization.Serializable

@Serializable
class GetReceiveRemote (
    val category: String? = null
)

@Serializable
data class GetResponseRemote(
    val list : List<Product>
)

@Serializable
data class Product(
    val productId: String,
    val name: String,
    val type: String,
    val photoIds : List<String> = emptyList(),
    val description: String
)