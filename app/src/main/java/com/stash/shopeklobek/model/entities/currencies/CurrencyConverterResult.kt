package com.stash.shopeklobek.model.entities.currencies

import com.google.gson.annotations.SerializedName

data class CurrencyConverterResult(
    @SerializedName( "PHP_EUR")
    val eur: Double,
    @SerializedName( "USD_EGP")
    val egp: Double
)