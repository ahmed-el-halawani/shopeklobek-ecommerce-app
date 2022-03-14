package com.stash.shopeklobek.model.entities.retroOrder

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class Order(

    @SerializedName( "id")
    val id: Long? = 0,

    @SerializedName( "email")
    val email: String? = "",

    @SerializedName( "app_id")
    val orderNumber: Long? = 0,

    @SerializedName( "created_at")
    val createdAt: String? = "",

    @SerializedName( "current_subtotal_price")
    val finalPrice: String? = "",

    @SerializedName( "current_total_discounts")
    val totalDiscount: String? = "",

//    @SerializedName( "quantity")
//    val quantity: Long? = 0,

    @SerializedName( "financial_status")
    val financialStatus: String? = "",

    @SerializedName( "line_items")
    val items: List<OrderDetails>? = listOf(),

    ): Parcelable
