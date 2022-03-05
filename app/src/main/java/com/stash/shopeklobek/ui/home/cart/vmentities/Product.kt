package com.stash.shopeklobek.ui.home.cart.vmentities

data class Product(
    val id: Long,
    val name: String,
    val image: String,
    val brand: String,
    val price: String,
    var count: Int = 0,
    var variant_id:Long? =0
)
