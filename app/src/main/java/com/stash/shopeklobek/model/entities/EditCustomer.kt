package com.stash.shopeklobek.model.entities

import com.google.gson.annotations.SerializedName

data class EditCustomer(
    @SerializedName( "id")
    val customerId: Long? = 0,

    @SerializedName( "email")
    val email: String?,


    @SerializedName( "phone")
    val phone: String? = "",


    @SerializedName( "first_name")
    val firstName: String? = "",

    @SerializedName( "last_name")
    val lastName: String? = "",

)