package com.stash.shopeklobek.model.entities.autocomplete_places_2

data class PlacesResult2(
    val features: List<Feature>,
    val query: Query,
    val type: String
)