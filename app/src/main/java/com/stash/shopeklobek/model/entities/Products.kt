package com.stash.shopeklobek.model.entities

import com.google.gson.annotations.SerializedName

data class Products(
    @SerializedName( "id")
    val productId: Long?,

    @SerializedName( "title")
    val title: String?,

    @SerializedName( "product_type")
    val productType: String?,

    @SerializedName( "body_html")
    val description:String ?,

    @SerializedName( "status")
    val status: String?,

    @SerializedName( "vendor")
    val vendor: String?,

    @SerializedName( "variants")
    val variants: List<Variants?> = listOf(),


    @SerializedName( "options")
    val options: List<Options?> = listOf(),

    @SerializedName( "image")
    val image: Images,

    @SerializedName( "images")
    val images: List<Images>



)
