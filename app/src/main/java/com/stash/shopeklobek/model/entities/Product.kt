package com.stash.shopeklobek.model.entities

import androidx.room.Entity
import com.google.gson.annotations.SerializedName

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

data class ProductModel(
    @SerializedName( "product" ) val product:Products?
)

