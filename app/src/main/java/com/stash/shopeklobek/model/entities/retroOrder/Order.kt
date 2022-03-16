package com.stash.shopeklobek.model.entities.retroOrder

import android.os.Parcelable
import android.util.Log
import com.google.gson.annotations.SerializedName
import com.stash.shopeklobek.model.entities.BillingShippingAddress
import com.stash.shopeklobek.utils.ViewHelpers.getLocale
import kotlinx.parcelize.Parcelize
import java.lang.Exception
import java.text.SimpleDateFormat
import java.util.*

@Parcelize
data class Order(

    @SerializedName( "id")
    val id: Long? = 0,

    @SerializedName( "email")
    val email: String? = "",

    @SerializedName( "billing_address")
    val billingAddress: BillingShippingAddress?,

    @SerializedName( "app_id")
    val orderNumber: Long? = 0,

    @SerializedName( "created_at")
    val createdAt: String? = "",

    @SerializedName( "current_subtotal_price")
    val finalPrice: String? = "",

    @SerializedName( "current_total_discounts")
    val totalDiscount: String? = "",



//    @SerializedName( "quantity")
//    val quantity: Long? = 0,

    @SerializedName( "financial_status")
    val financialStatus: String? = "",

    @SerializedName( "line_items")
    val items: List<OrderDetails>? = listOf(),

    ): Parcelable{
        fun getDate():String{
            return try {
                val sdf = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ", getLocale())
                val convertTo = SimpleDateFormat("MMMM,dd yyyy", getLocale())
                convertTo.format(sdf.parse(createdAt?:throw Exception("no Date"))?:throw
                Exception("null date"))
            }catch (t:Throwable){
                Log.e("getDate", "getDate: "+t.message )
                createdAt?:""
            }
        }
    }
