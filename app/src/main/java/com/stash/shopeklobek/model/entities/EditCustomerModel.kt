package com.stash.shopeklobek.model.entities

import com.google.gson.annotations.SerializedName

data class EditCustomerModel(
    @SerializedName( "customer")
    val customer: EditCustomer,

    @SerializedName( "error")
    val error: Error? = null,

    )