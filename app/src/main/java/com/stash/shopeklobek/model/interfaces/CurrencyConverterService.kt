package com.stash.shopeklobek.model.interfaces

import com.stash.shopeklobek.model.entities.currencies.CurrencyConverterResult
import com.stash.shopeklobek.utils.Constants
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface CurrencyConverterService {
    @GET("/api/v7/convert?q=USD_EGP,USD_EUR&compact=ultra")
    suspend fun getCurrenciesValueNow(
        @Query("apiKey") apiKey:String = Constants.CURRENCY_CONVERTER_API_KEY
    ): Response<CurrencyConverterResult>
}