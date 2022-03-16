package com.stash.shopeklobek.model.entities.places

import com.google.android.gms.maps.model.LatLng

data class PlacesResultItem(
    val adminDivision1: AdminDivision?,
    val adminDivision2: AdminDivision?,
    val coordinates: Coordinates?,
    val country: Country?,
    val elevation: Int?,
    val geonameId: Int?,
    val id: String,
    val name: String?,
    val population: Int?,
    val score: Double?,
    val timezoneId: String?,
    val type: String?
) {
    fun getLatLng() = coordinates?.let { LatLng(coordinates.latitude, coordinates.longitude) }

    fun generateAddress() =
        "${name?.let { "$it," }} " +
                "${adminDivision1?.name.let { "$it," }} " +
                "${country?.name.let { "$it," }} "
}