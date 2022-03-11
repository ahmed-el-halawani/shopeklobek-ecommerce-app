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
import com.stash.shopeklobek.model.api.ShopifyApi
import com.stash.shopeklobek.model.repositories.ProductRepo
import com.stash.shopeklobek.model.shareprefrances.SettingsPreferences
import com.stash.shopeklobek.utils.Constants
import com.stash.shopeklobek.utils.CurrencyUtil
import com.stash.shopeklobek.utils.ViewHelpers
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MyApplication : Application() {


    override fun onCreate() {
        CurrencyUtil.application = this
        Hawk.init(this).build()
        super.onCreate()

        PayPalCheckout.setConfig(
            CheckoutConfig(
                application = this,
                clientId = Constants.PAYPAL_CLIENT_ID,
                environment = Environment.SANDBOX,
                returnUrl = BuildConfig.APPLICATION_ID + "://paypalpay",
                currencyCode = CurrencyCode.USD,
                userAction = UserAction.PAY_NOW,
                settingsConfig = SettingsConfig(
                    loggingEnabled = true
                )
            )
        )

    }
}