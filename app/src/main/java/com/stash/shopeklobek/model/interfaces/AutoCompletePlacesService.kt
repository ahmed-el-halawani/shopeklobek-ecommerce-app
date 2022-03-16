package com.stash.shopeklobek.model.interfaces

import com.stash.shopeklobek.model.entities.autocomplete_places_2.PlacesResult2
import com.stash.shopeklobek.model.entities.places.PlacesResult
import com.stash.shopeklobek.utils.Constants
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface AutoCompletePlacesService {
    @GET("autocomplete")
    suspend fun getAutoCompletePlaces(
        @Query("text") q: String,
        @Query("limit") limit: Int = 10,
        @Query("apiKey") apikey: String = Constants.PLACES2_API_KEY_URL,
    ): Response<PlacesResult2>
}