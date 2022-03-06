package com.stash.shopeklobek.model.entities

import com.google.gson.annotations.SerializedName

data class ProductModel(
    @SerializedName( "product" ) val product: Products?
)