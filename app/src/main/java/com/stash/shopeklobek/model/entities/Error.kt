package com.stash.shopeklobek.model.entities

import com.google.gson.annotations.SerializedName

data class Error(
    @SerializedName( "email")
    val email: List<String>? = listOf(),

    @SerializedName( "phone")
    val phone: List<String>? = listOf(),
)
