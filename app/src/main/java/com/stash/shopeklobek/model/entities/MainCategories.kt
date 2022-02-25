package com.stash.shopeklobek.model.entities

import com.google.gson.annotations.SerializedName

data class MainCategories(
    @SerializedName( "custom_collections")
    val collections:List<MainCollections>? = listOf(),
)
