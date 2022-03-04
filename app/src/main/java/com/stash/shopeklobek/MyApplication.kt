package com.stash.shopeklobek

import android.app.Application
import android.util.Log
import com.orhanobut.hawk.Hawk
import com.paypal.checkout.PayPalCheckout
import com.paypal.checkout.config.CheckoutConfig
import com.paypal.checkout.config.Environment
import com.paypal.checkout.config.SettingsConfig
import com.paypal.checkout.createorder.CurrencyCode
import com.paypal.checkout.createorder.UserAction

class MyApplication: Application() {
    override fun onCreate() {
        Hawk.init(this).build()
        super.onCreate()

    }
}