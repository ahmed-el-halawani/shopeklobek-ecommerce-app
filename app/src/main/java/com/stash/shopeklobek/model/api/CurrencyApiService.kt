package com.stash.shopeklobek.model.api

import com.stash.shopeklobek.model.entities.currencies.CurrencyConverterResult
import com.stash.shopeklobek.model.interfaces.CurrencyConverterService
import com.stash.shopeklobek.model.interfaces.ShopifyServices
import com.stash.shopeklobek.utils.Constants
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object CurrencyApiService {
    private lateinit var retrofit: Retrofit
    private val retro: Retrofit by lazy {
        retrofit = Retrofit.Builder()
            .baseUrl(Constants.CURRENCY_CONVERTER_API_BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        retrofit
    }

    var currencyConverterApi = retro.create(CurrencyConverterService::class.java)
}