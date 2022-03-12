package com.stash.shopeklobek.model.entities.room

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import com.stash.shopeklobek.model.entities.*
import com.stash.shopeklobek.utils.Constants.CART_TABLE
import com.stash.shopeklobek.utils.Constants.FAVORITES_TABLE
import com.stash.shopeklobek.utils.Constants.ORDER_TABLE
import kotlinx.parcelize.Parcelize


@Entity(
    tableName = ORDER_TABLE
)

@Parcelize
data class RoomOrder(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val customerEmail:String,
    val order:Order
) : Parcelable