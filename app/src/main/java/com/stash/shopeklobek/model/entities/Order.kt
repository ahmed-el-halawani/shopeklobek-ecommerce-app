package com.stash.shopeklobek.model.entities
import com.google.gson.annotations.SerializedName

data class Order(
    @SerializedName( "current_subtotal_price")
    val finalPrice: String? = "",
    @SerializedName( "financial_status")
    val financialStatus: String? = "",
    @SerializedName( "created_at")
    val createdAt: String? = "",

    @SerializedName( "id")
    val customerId: Long? = 0,

    @SerializedName( "app_id")
    val orderNumber: Long? = 0,





    @SerializedName( "current_total_discounts")
    val totalDiscount: String? = "",

//    @SerializedName( "quantity")
//    val quantity: Long? = 0,



    @SerializedName( "line_items")
    val items: List<OrderDetails>? = listOf(),

    ){
    val dat = createdAt
    val price = finalPrice
    val state = financialStatus
}
