package com.stash.shopeklobek.model.entities

data class Product(
    val id: Long,
    val name: String,
    val image: String,
    val brand: String,
    val price: String,
    var count: Int = 0,
    var inWish: Boolean = false,
    var inCart: Boolean = false,
    var variant_id:Long? =0
)

