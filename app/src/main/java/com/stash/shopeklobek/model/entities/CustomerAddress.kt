package com.stash.shopeklobek.model.entities

import com.google.gson.annotations.SerializedName

data class CustomerAddress(
    @SerializedName( "id")
    val id: Long? = 0,

    @SerializedName( "customer_id")
    val customerId:Long? = 0,

    @SerializedName( "address1")
    val address:String? = "",

    @SerializedName( "first_name")
    val firstName:String? = "",

    @SerializedName( "last_name")
    val lastName:String? = "",

    @SerializedName( "zip")
    val zip: String? = "",

    @SerializedName( "city")
    val city: String? = "",

    @SerializedName( "phone")
    val phone: String? = "",
)