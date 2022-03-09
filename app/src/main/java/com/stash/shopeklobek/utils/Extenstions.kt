package com.stash.shopeklobek.utils

import android.content.Context
import com.stash.shopeklobek.model.entities.LineItems
import com.stash.shopeklobek.model.entities.OrderDetails
import com.stash.shopeklobek.model.entities.room.RoomCart

fun List<RoomCart>.getPrice():Double{
    var price = 0.0
    this.forEach {
        price += it.count * (it.variant()?.price?.toDouble()?:1.0)
    }
    return price
}

fun List<RoomCart>.toLineItem():List<OrderDetails>{
    return this.map {
        OrderDetails(
            quantity = it.count.toLong(),
            price = (it.count * (it.variant()?.price?.toDouble()?:1.0)).toString()


        )
    }

}

fun Double.toCurrency(context:Context):String{
    return CurrencyUtil.convertCurrency(this,context)
}

fun String.toCurrency(context:Context):String{
    return CurrencyUtil.convertCurrency(this,context)
}