package com.stash.shopeklobek.model.entities

import com.google.gson.annotations.SerializedName

data class LineItems(
    @SerializedName( "variant_id")
    val variantId: Long? = 0,

    @SerializedName( "quantity")
    val quantity: Long? = 0,
)
