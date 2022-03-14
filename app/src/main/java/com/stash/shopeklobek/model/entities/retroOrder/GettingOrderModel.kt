package com.stash.shopeklobek.model.entities.retroOrder

import com.google.gson.annotations.SerializedName

data class GettingOrderModel(
    @SerializedName( "order")
    val order: Order? = null,

    )
