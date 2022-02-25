package com.stash.shopeklobek.model.entities

import com.google.gson.annotations.SerializedName

data class BillingShippingAddress(
    @SerializedName( "first_name")
    val firstName: String? = "",
    @SerializedName( "last_name")
    val lastName: String? = "",
    @SerializedName( "address1")
    val address: String? = "",
    @SerializedName( "city")
    val city: String? = "",
    @SerializedName( "country")
    val country: String? = "",
)
