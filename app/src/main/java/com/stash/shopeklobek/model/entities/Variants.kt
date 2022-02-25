package com.stash.shopeklobek.model.entities

import com.google.gson.annotations.SerializedName

data class Variants(

    @SerializedName( "id")
    val id: Long?,

    @SerializedName( "price")
    val price:String?,

    @SerializedName( "inventory_quantity")
    val quantity:Int?,

    @SerializedName( "requires_shipping")
    val requiresShipping:Boolean?,

    @SerializedName( "inventory_management")
    val inventoryManagement:String?,

    @SerializedName( "taxable")
    val taxable: Boolean?,

)
