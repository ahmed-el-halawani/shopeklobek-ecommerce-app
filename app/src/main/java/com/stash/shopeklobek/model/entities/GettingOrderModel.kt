package com.stash.shopeklobek.model.entities

import com.google.gson.annotations.SerializedName

data class GettingOrderModel(
    @SerializedName( "order")
    val order: Order? = null,
)
