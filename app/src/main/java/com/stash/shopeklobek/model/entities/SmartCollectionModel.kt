package com.stash.shopeklobek.model.entities

import com.google.gson.annotations.SerializedName

data class SmartCollectionModel(
    @SerializedName( "smart_collections")
    val smart_collections : List<SmartCollection>? = listOf()
)
