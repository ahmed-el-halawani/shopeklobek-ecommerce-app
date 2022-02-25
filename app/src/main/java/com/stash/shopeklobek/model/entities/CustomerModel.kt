package com.stash.shopeklobek.model.entities

import com.google.gson.annotations.SerializedName

data class CustomerModel (
    @SerializedName( "customer")
    val customer: Customer?,

    @SerializedName( "error")
    val error: Error? = null,
)

