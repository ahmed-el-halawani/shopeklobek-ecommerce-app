package com.stash.shopeklobek.model.api

import com.stash.shopeklobek.BuildConfig
import com.stash.shopeklobek.model.interfaces.ShopifyServices
import com.stash.shopeklobek.utils.Constants
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object ApiService {
    private lateinit var retrofit: Retrofit
    private val retro: Retrofit by lazy {
        retrofit = Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .client(buildAuthClient())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        retrofit
    }

     var api = retro.create(ShopifyServices::class.java)

    private fun buildAuthClient(): OkHttpClient {

        val httpClient = OkHttpClient.Builder()
        httpClient.addInterceptor( provideLoggingInterceptor())
        httpClient.connectTimeout(60, TimeUnit.SECONDS)
        httpClient.readTimeout(60, TimeUnit.SECONDS)
        return httpClient.build()
    }

    private fun provideLoggingInterceptor(): HttpLoggingInterceptor {
        val interceptor = HttpLoggingInterceptor()

        @Suppress("ConstantConditionIf")
        if (BuildConfig.DEBUG) {
            interceptor.level = HttpLoggingInterceptor.Level.BODY
        } else {
            interceptor.level = HttpLoggingInterceptor.Level.NONE
        }

        return interceptor
    }
}