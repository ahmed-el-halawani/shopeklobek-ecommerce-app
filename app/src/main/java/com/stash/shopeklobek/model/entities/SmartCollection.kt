package com.stash.shopeklobek.model.entities

import com.google.gson.annotations.SerializedName

data class SmartCollection(
    @SerializedName( "id")
    val id:Long,
    @SerializedName( "title")
    val title: String,
    @SerializedName( "image")
    val image: Images
)
