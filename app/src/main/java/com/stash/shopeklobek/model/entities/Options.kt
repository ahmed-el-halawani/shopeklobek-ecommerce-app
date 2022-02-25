package com.stash.shopeklobek.model.entities

import com.google.gson.annotations.SerializedName

data class Options(
    @SerializedName( "values")
    val values:List<String>? = listOf()
)
