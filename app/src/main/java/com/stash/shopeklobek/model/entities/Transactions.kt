package com.stash.shopeklobek.model.entities

import com.google.gson.annotations.SerializedName

data class Transactions(
    @SerializedName( "kind")
    val kind: String? = "",

    @SerializedName( "status")
    val status: String? = "",

    @SerializedName( "amount")
    val amount: Double? = 0.0,
)
