package com.stash.shopeklobek.model.entities

import com.google.gson.annotations.SerializedName

data class PriceRule(
    @SerializedName( "id")
    val  id: Long? = 0,

    @SerializedName( "title")
    val title: String?,

    @SerializedName( "target_type")
    val targetType:String?,

    @SerializedName( "target_selection")
    val targetSelection:String?,

    @SerializedName( "allocation_method")
    val allocationMethod:String?,

    @SerializedName( "value_type")
    val valueType:String?,

    @SerializedName( "value")
    val value:String?,

    @SerializedName( "customer_selection")
    val customerSelection:String?,

    @SerializedName( "starts_at")
    val startsAt:String?,



){
    fun toDiscountCode():DiscountCodes{
        return DiscountCodes(
            code = title,
            amount = value,
            type = valueType
        )
    }
}
