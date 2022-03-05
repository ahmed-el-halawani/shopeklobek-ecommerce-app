package com.stash.shopeklobek.model.entities.room

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import com.stash.shopeklobek.model.entities.*
import com.stash.shopeklobek.utils.Constants.CART_TABLE
import com.stash.shopeklobek.utils.Constants.FAVORITES_TABLE


@Entity(
    tableName = CART_TABLE
)
data class RoomCart(
    @PrimaryKey val id: Long,
    val product:Products,
    val variantId:Long?=null,
    var count:Int =1,
){
    fun variant():Variants?{
        return product.variants.first{
            it?.id == variantId
        }
    }

}

