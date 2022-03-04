package com.stash.shopeklobek.model.entities

import com.google.gson.annotations.SerializedName

data class MainCollections(
    @SerializedName( "id")
    val collectionsId: Long?,

    @SerializedName( "handle")
    val collectionsHandle: String?,

    @SerializedName( "image")
    val collectionsImage: MainCollectionsImage?

)
