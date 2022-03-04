package com.stash.shopeklobek.model.room.utils

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.newcore.wezy.localDb.utils.ConverterHelper
import com.stash.shopeklobek.model.entities.Order
import com.stash.shopeklobek.model.entities.Products

class Converters {

    @TypeConverter
    fun fromProducts(products: Products): String = ConverterHelper.toJson(products)

    @TypeConverter
    fun toProducts(json: String): Products {
        return Gson().fromJson(json, Products::class.java)
    }

    @TypeConverter
    fun fromOrder(order: Order): String = ConverterHelper.toJson(order)

    @TypeConverter
    fun toOrder(json: String): Order {
        return Gson().fromJson(json, Order::class.java)
    }

}