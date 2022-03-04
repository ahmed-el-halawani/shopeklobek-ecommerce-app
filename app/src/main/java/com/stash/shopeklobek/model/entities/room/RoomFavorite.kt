package com.stash.shopeklobek.model.entities.room

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import com.stash.shopeklobek.model.entities.*
import com.stash.shopeklobek.utils.Constants.FAVORITES_TABLE


@Entity(
    tableName = FAVORITES_TABLE
)
data class RoomFavorite(
    @PrimaryKey val id: Long,
    val product:Products
)