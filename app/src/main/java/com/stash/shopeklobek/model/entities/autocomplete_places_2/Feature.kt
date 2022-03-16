package com.stash.shopeklobek.model.entities.autocomplete_places_2

data class Feature(
    val bbox: List<Double>,
    val geometry: Geometry,
    val properties: Properties,
    val type: String
)