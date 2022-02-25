package com.stash.shopeklobek.model.entities

import com.google.gson.annotations.SerializedName

data class OrderDetails(
    @SerializedName( "fulfillable_quantity")
    val quantity: Long? = 0,
    @SerializedName( "name")
    val name: String? = "",
    @SerializedName( "price")
    val price: String? = "",
    @SerializedName( "vendor")
    val vendor: String? = "",
    @SerializedName( "product_id")
    val id: Long = 0
)

