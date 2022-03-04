package com.stash.shopeklobek.utils

import okhttp3.Credentials
import okhttp3.Interceptor

class BasciInterceptor (username: String, password: String): Interceptor {
    private var credentials: String = Credentials.basic(username, password)

    override fun intercept(chain: Interceptor.Chain): okhttp3.Response {
        var request = chain.request()
        request = request.newBuilder().header("Authorization", credentials).build()
        return chain.proceed(request)
    }
}