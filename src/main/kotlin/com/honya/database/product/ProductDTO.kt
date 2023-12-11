package com.honya.database.product

class ProductDTO (
    val productId: String,
    val name : String,
    val type: String,
    val photoIds: List<String>?,
    val description: String
)