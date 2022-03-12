package com.stash.shopeklobek.utils


object Constants {

    // sharedPreferences Tags
    const val ALL_DATA_ROUTE = "ALL_DATA_ROUTE"
    const val TAG = "TAG"

    //api

     const val apiKey = "bfe73f4cd7e7f8737d5928b2a439022e"
     const val password = "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"
    const val BASE_URL = "https://$apiKey:$password@jets2022.myshopify.com/admin/api/2022-01/"
    const val CURRENCY_CONVERTER_API_KEY = "3e3890a19a7caef23423"
    const val CURRENCY_CONVERTER_API_BASE_URL= "https://free.currconv.com/api/v7/convert/"
    const val EXCHANGE_CURRENCY_CONVERTER_API_BASE_URL= "https://v6.exchangerate-api.com/v6/e2eb8a1d8527b64e0a070175/"


    //paypal
    const val PAYPAL_CLIENT_ID = "AbUpfUV90zq_ES0bzQVCb5f2UPXPsXZA3xsG2CQTUDyD3q8RLZjCZMcwLIWfExuPzcqziEjwZHtVlQma"

    //room
    const val FAVORITES_TABLE = "FAVORITES_TABLE"
    const val CART_TABLE = "CART_TABLE"
    const val ORDER_TABLE = "ORDER_TABLE"
    const val DATABASE_NAME = "shopeklobek"

    //file shared
    const val FILE_NAME = "FILTER_SHARED_PREFERENCE"
    const val FIRST_FILTER_CATEGORIES = "FIRST_FILTER_CATEGORIES"
    const val SECOND_FILTER_CATEGORIES = "SECOND_FILTER_CATEGORIES"
    const val FIRST_FILTER_PRICE = "FIRST_FILTER_PRICE"




/*
    get request

    end point : https://shopeklobek2.myshopify.com/admin/api/2022-01/products.json

    Content-Type:application/json
    X-Shopify-Access-Token:API_KEY

     */




}