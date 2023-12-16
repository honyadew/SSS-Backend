package com.honya.database.product

enum class ProductType(
    val key: String
) {
    STICKER (key = "sticker"),
    BADGE (key = "badge"),
    MAGNET (key = "magnet"),
    POSTER (key = "poster"),
    OTHER (key = "other");

    companion object{
        private val map = enumValues<ProductType>().associateBy { it.key }

        fun fromKey(type: String): ProductType? {
            return map[type]
        }
    }
}