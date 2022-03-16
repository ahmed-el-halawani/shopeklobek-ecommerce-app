package com.stash.shopeklobek.model.entities

import android.os.Parcelable
import com.google.android.gms.maps.model.LatLng
import com.google.gson.annotations.SerializedName
import com.stash.shopeklobek.utils.latLngFromJson
import kotlinx.parcelize.Parcelize
import com.paypal.checkout.order.Address as paypalAddress

@Parcelize
data class Address(

    @SerializedName("id")
    val id: Long? = 0,

    @SerializedName("address1")
    val address1: String? = "",

    @SerializedName("address2")
    val address: String? = "",

    @SerializedName("city")
    val city: String? = "",

    @SerializedName("country")
    val country: String? = "",


    @SerializedName("company")
    val latLng: String? = "",

    @SerializedName("first_name")
    val firstName: String? = "",

    @SerializedName("last_name")
    val lastName: String? = "",

    @SerializedName("zip")
    val zip: String? = "",

    @SerializedName("default")
    val default: Boolean = false,

    @SerializedName("phone")
    val phone: String? = "",

    ) : Parcelable {

    fun generateAddressLine(): String {
        return address1 ?: ""
    }

    fun toPaypalAddress(): paypalAddress = paypalAddress(
        countryCode = "EG",
        addressLine1 = address1,
        addressLine2 = address,
        postalCode = zip,
        adminArea1 = country,
        adminArea2 = city,
    )

    fun getLatitude(): String? {
        return latLng?.latLngFromJson()?.latitude?.toString()
    }

    fun getLongitude(): String? {
        return latLng?.latLngFromJson()?.longitude?.toString()
    }

    fun getLatLng():LatLng?{
        return latLng?.latLngFromJson()
    }



}
