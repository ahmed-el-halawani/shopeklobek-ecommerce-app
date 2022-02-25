package com.stash.shopeklobek.model.entities

import com.google.gson.annotations.SerializedName

data class Discount(
    @SerializedName( "price_rule")
    val discount: PriceRule?,
)
