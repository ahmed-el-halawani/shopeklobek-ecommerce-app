package com.stash.shopeklobek.model.entities.autocomplete_places_2

data class Rank(
    val confidence: Int,
    val confidence_city_level: Int,
    val importance: Double,
    val match_type: String
)