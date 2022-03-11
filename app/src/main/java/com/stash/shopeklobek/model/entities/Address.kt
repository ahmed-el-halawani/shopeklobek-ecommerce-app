package com.stash.shopeklobek.model.entities

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import com.paypal.checkout.PayPalCheckout
import com.paypal.checkout.order.Address as paypalAddress
import kotlinx.parcelize.Parcelize

@Parcelize
data class Address(

    @SerializedName( "id")
    val id: Long? = 0,

    @SerializedName( "address1")
    val address: String? = "",

    @SerializedName( "city")
    val city: String? = "",

    @SerializedName( "first_name")
    val firstName: String? = "",

    @SerializedName( "last_name")
    val lastName: String? = "",

    @SerializedName( "zip")
    val zip: String? = "",

    @SerializedName( "default")
    val default: Boolean = false,

    @SerializedName( "phone")
    val phone: String? = "",

): Parcelable {
    fun generateAddressLine():String{
        return "$city, $address"
    }

    fun toPaypalAddress():paypalAddress = paypalAddress(
        countryCode = "EG",
        addressLine1 = address,
        addressLine2 = address,
        postalCode = zip,
        adminArea1 = address,
        adminArea2 = address,
    )


}
