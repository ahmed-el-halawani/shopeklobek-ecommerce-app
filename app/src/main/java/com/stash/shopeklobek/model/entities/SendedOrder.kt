package com.stash.shopeklobek.model.entities

import com.google.gson.annotations.SerializedName

data class SendedOrder(

    @SerializedName( "email")
    val email: String? = "",

    @SerializedName( "financial_status") //avoid , authorized
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
