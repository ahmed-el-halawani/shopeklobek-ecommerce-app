package com.stash.shopeklobek.model.entities.retroOrder

import com.google.gson.annotations.SerializedName
import com.stash.shopeklobek.model.entities.BillingShippingAddress
import com.stash.shopeklobek.model.entities.DiscountCodes
import com.stash.shopeklobek.model.entities.LineItems
import com.stash.shopeklobek.model.entities.Transactions


enum class FinancialStatus {
    Voided {
        override fun toString(): String {
            return "voided"
        }
    },
    Paid {
        override fun toString(): String {
            return "paid"
        }
    };

}

data class SendOrderModel(
    val order: SendedOrder
)

data class SendedOrder(

    @SerializedName( "email")
    val email: String? = "",

    @SerializedName( "financial_status")
    val financialStatus: String? = "",

    @SerializedName( "billing_address")
    val billingAddress: BillingShippingAddress? = null,

    @SerializedName( "shipping_address")
    val shippingAddress: BillingShippingAddress? = null,

    @SerializedName( "transactions")
    val transactions: List<Transactions?> = listOf(),

    @SerializedName( "discount_codes")
    val discountCodes: List<DiscountCodes?> = listOf(),

    @SerializedName( "line_items")
    val lineItems: List<LineItems?> = listOf(),


    )
