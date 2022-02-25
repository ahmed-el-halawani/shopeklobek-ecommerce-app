package com.stash.shopeklobek.model.entities

import com.google.gson.annotations.SerializedName

data class ProductsModel(

    @SerializedName( "products")
    val product: List<Products> = listOf()

)
