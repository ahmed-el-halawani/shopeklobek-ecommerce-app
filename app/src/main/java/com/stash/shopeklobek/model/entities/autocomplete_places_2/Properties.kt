package com.stash.shopeklobek.model.entities.autocomplete_places_2

import com.google.android.gms.maps.model.LatLng

data class Properties(
    val address_line1: String?,
    val address_line2: String?,
    val category: String?,
    val city: String?,
    val country: String?,
    val country_code: String?,
    val county: String?,
    val county_code: String?,
    val datasource: Datasource,
    val district: String?,
    val formatted: String?,
    val hamlet: String?,
    val lat: Double?,
    val lon: Double?,
    val name: String?,
    val place_id: String?,
    val postcode: String?,
    val state: String?,
    val state_code: String?,
    val village: String?
) {
    fun generateAddress() =
        "${hamlet?.let { "$it," } ?: ""} " +
                "${city?.let { "$it," } ?: ""} " +
                "${state?.let { "$it," } ?: ""} " +
                "${country ?: ""} "

    fun getLatLng(): LatLng? {
        if (lat == null || lon == null)
            return null
        return LatLng(lat, lon)
    }
}