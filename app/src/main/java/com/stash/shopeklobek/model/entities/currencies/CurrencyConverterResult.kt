package com.stash.shopeklobek.model.entities.currencies

import com.google.gson.annotations.SerializedName

data class CurrencyConverterResult(
    @SerializedName( "EGP_SAR")
    val sar: Double,
    @SerializedName( "EGP_USD")
    val usd: Double
)