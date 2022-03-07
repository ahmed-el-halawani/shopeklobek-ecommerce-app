package com.stash.shopeklobek.model.entities

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
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

) : Parcelable
