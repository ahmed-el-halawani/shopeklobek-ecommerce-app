package com.stash.shopeklobek

import android.app.Application
import android.util.Log
import com.paypal.checkout.PayPalCheckout
import com.paypal.checkout.config.CheckoutConfig
import com.paypal.checkout.config.Environment
import com.paypal.checkout.config.SettingsConfig
import com.paypal.checkout.createorder.CurrencyCode
import com.paypal.checkout.createorder.UserAction

class MyApplication: Application() {
    override fun onCreate() {
        super.onCreate()


        val config = CheckoutConfig(
            application = this,
            clientId = "AbUpfUV90zq_ES0bzQVCb5f2UPXPsXZA3xsG2CQTUDyD3q8RLZjCZMcwLIWfExuPzcqziEjwZHtVlQma",
            environment = Environment.SANDBOX,
            returnUrl = BuildConfig.APPLICATION_ID+"://paypalpay",
            currencyCode = CurrencyCode.USD,
            userAction = UserAction.PAY_NOW,
            settingsConfig = SettingsConfig(
                loggingEnabled = true
            )
        )
        PayPalCheckout.setConfig(config)
    }

}