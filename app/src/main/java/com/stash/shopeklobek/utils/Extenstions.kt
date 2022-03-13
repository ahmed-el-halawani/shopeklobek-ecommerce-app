package com.stash.shopeklobek.utils

import android.content.Context
import com.paypal.checkout.createorder.CurrencyCode
import com.paypal.checkout.order.Items
import com.paypal.checkout.order.UnitAmount
import com.stash.shopeklobek.model.entities.room.RoomCart

fun List<RoomCart>.getPrice(): Double {
    var price = 0.0
    this.forEach {
        price += it.count * (it.variant()?.price?.toDouble() ?: 1.0)
    }
    return price
}

fun List<RoomCart>.toItems(): List<Items> {
    return this.map {

        Items.Builder().name(it.product.title ?: "")
            .quantity(it.count.toString())
            .unitAmount(
                UnitAmount(
                    currencyCode = CurrencyCode.USD,
                    value = (it.variant()?.price?.toDouble() ?: 1.0).toString(),
                )
            ).build()

    }

}

fun Double.toCurrency(context: Context): String {
    return CurrencyUtil.convertCurrency(this, context)
}

fun String.toCurrency(context: Context): String {
    return CurrencyUtil.convertCurrency(this, context)
}