package com.stash.shopeklobek.utils

import android.content.Context
import com.paypal.checkout.createorder.CurrencyCode
import com.paypal.checkout.order.Items
import com.paypal.checkout.order.UnitAmount
import com.stash.shopeklobek.model.entities.*
import com.stash.shopeklobek.model.entities.retroOrder.Order
import com.stash.shopeklobek.model.entities.retroOrder.OrderDetails
import com.stash.shopeklobek.model.entities.room.RoomCart
import com.stash.shopeklobek.model.entities.room.RoomOrder
import kotlin.math.absoluteValue

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

fun List<RoomCart>.toOrderDetails(): List<OrderDetails> {
    return map {
        OrderDetails(
            quantity = it.count.toLong(),
            name = it.product.title,
            price = it.variant()?.price,
            vendor = it.product.vendor,
            id = it.product.productId ?: it.id
        )
    }
}

fun List<Order>.toRoomOrder(): List<RoomOrder> {
    return map {
        RoomOrder(
            id = it.id ?: 0,
            customerEmail = it.email ?: "",
            order = it
        )
    }
}


fun List<RoomCart>.toLineItem(): List<LineItems> {
    return map {
        LineItems(
            quantity = it.count.toLong(),
            variantId = it.variant()?.id,
        )
    }
}


fun PriceRule.toDiscountCodes(): List<DiscountCodes>? {
    return listOf(
        DiscountCodes(
            code = title,
            amount = value?.toDouble()?.absoluteValue.toString(),
            type = targetType
        )
    )
}

fun Address.toBillingShippingAddress(): BillingShippingAddress {
    return BillingShippingAddress(
        firstName = firstName,
        lastName = lastName,
        address = address,
        city = city,
        country = city,
        phone = phone,
        latitude = latitude,
        longitude = longitude,
        address1 = address1,
    )
}

fun Double.toCurrency(context: Context): String {
    return CurrencyUtil.convertCurrency(this, context)
}

fun String.toCurrency(context: Context): String {
    return CurrencyUtil.convertCurrency(this, context)
}