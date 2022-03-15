package com.stash.shopeklobek.model.interfaces

import com.stash.shopeklobek.model.entities.places.PlacesResult
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface PlacesServices {
    @GET("autocomplete")
    suspend fun getAutoCompletePlaces(
        @Query("q") q: String,
        @Query("limit") limit: Int = 10,
        @Query("skip") skip: Int = 0,
        @Query("language") language: String = "ar",
        @Query("type") type: String = "City",
    ): Response<PlacesResult>
}