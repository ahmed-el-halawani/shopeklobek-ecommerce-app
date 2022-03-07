package com.stash.shopeklobek.model.entities.room

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import com.stash.shopeklobek.model.entities.*
import com.stash.shopeklobek.utils.Constants.CART_TABLE
import com.stash.shopeklobek.utils.Constants.FAVORITES_TABLE
import com.stash.shopeklobek.utils.Constants.ORDER_TABLE


@Entity(
    tableName = ORDER_TABLE
)
data class RoomOrder(
    @PrimaryKey val id: Long,
    val customerEmail:String,
    val order:Order
)