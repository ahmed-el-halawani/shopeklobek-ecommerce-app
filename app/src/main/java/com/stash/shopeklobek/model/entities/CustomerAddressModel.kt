package com.stash.shopeklobek.model.entities

import com.google.gson.annotations.SerializedName

data class CustomerAddressModel(
    @SerializedName( "customer_address")
    val customerAddress: CustomerAddress?,
)
