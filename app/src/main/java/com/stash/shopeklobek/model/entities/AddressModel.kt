package com.stash.shopeklobek.model.entities

import com.google.gson.annotations.SerializedName


data class AddressModel(
    @SerializedName( "address")
    val address: Address?,
)
