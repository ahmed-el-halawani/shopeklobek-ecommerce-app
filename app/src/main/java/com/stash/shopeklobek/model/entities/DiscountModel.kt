package com.stash.shopeklobek.model.entities

import com.google.gson.annotations.SerializedName

data class DiscountModel(
    @SerializedName( "price_rules")
    val discount: List<PriceRule>?,
)