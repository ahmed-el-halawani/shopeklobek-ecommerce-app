package com.stash.shopeklobek.model.entities

import com.google.gson.annotations.SerializedName
import com.stash.shopeklobek.model.entities.room.RoomCart
import com.stash.shopeklobek.utils.ViewHelpers
import java.text.SimpleDateFormat
import java.util.*

enum class FinancialStatus(val value: String) {
    Paid("paid"), Voided("voided");
}


data class Order(
    @SerializedName("current_subtotal_price")
    val finalPrice: String? = "",
    @SerializedName("financial_status")
    val financialStatus: String? = "", //paid , voided
    @SerializedName("created_at")
    val createdAt: Long = 0,

    @SerializedName("billing_address")
    val billingAddress: Address? = null,

    @SerializedName("current_total_discounts")
    val totalDiscount: String? = "",

    @SerializedName("line_items")
    val items: List<RoomCart>? = listOf(),

    @SerializedName("id")
    val id: Long? = 0,

    @SerializedName("app_id")
    val orderNumber: Long? = 0,

    ) {

    val dat: String =
        SimpleDateFormat("EE, d MMM", ViewHelpers.getLocale())
            .format(Date(createdAt))

    fun getDate() = SimpleDateFormat("EE, d MMM", ViewHelpers.getLocale()).format(Date(createdAt))

    val price = finalPrice
    val state = financialStatus


}
