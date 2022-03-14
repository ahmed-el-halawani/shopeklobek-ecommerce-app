package com.stash.shopeklobek.model.entities.retroOrder

import com.google.gson.annotations.SerializedName


data class OrdersModels(
    @SerializedName( "orders")
    val order:List<Order?> = listOf()
)
