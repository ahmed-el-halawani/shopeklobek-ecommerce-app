package com.stash.shopeklobek.model.entities.retroOrder

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class OrderDetails(
    @SerializedName( "quantity")
    val quantity: Long? = 0,
    @SerializedName( "name")
    val name: String? = "",
    @SerializedName( "price")
    val price: String? = "",
    @SerializedName( "vendor")
    val vendor: String? = "",
    @SerializedName( "product_id")
    val id: Long = 0
): Parcelable

