package com.stash.shopeklobek.model.api

import com.stash.shopeklobek.model.interfaces.ExchangeCurrencyConverterService
import com.stash.shopeklobek.utils.Constants
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ExchangeCurrencyApi {
    private lateinit var retrofit: Retrofit
    private val retro: Retrofit by lazy {
        retrofit = Retrofit.Builder()
            .baseUrl(Constants.EXCHANGE_CURRENCY_CONVERTER_API_BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        retrofit
    }

    var exchangerateConverterApi = retro.create(ExchangeCurrencyConverterService::class.java)
}