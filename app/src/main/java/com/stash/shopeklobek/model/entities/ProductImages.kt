package com.stash.shopeklobek.model.entities

import com.google.gson.annotations.SerializedName

data class ProductImages(
    @SerializedName( "images")
    val images: List<Images>? = listOf(),
)

