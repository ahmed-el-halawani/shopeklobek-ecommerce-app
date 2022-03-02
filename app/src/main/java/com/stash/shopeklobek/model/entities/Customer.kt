package com.stash.shopeklobek.model.entities

import com.google.gson.annotations.SerializedName

data class Customer(
    @SerializedName( "id")
    val customerId: Long? = null,

    @SerializedName( "email")
    val email: String?,

    @SerializedName( "phone")
    val phone: String? = "",

    @SerializedName( "first_name")
    val firstName: String? = "",

    @SerializedName( "last_name")
    val lastName: String? = "",

    @SerializedName( "orders_count")
    val ordersCount: Int = 0,

    @SerializedName( "state")
    val state: String? = "",

    @SerializedName( "currency")
    val currency: String? = "EGP",

    @SerializedName( "note")
    val note: String? = "",

    @SerializedName( "total_spent")
    val totalSpent: String? = "",

    @SerializedName( "addresses")
    val addresses: List<Address>? = listOf(),

    @SerializedName( "password")
    val password: String? = "",

    @SerializedName( "password_confirmation")
    val passwordConfirmation: String? = "",
)
