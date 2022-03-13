package com.stash.shopeklobek.model.interfaces

import com.stash.shopeklobek.model.entities.currencies.ExchangerApiResponse
import retrofit2.Response
import retrofit2.http.GET

interface ExchangeCurrencyConverterService {
    @GET("latest/USD")
    suspend fun getCurrenciesValueNow(): Response<ExchangerApiResponse>
}