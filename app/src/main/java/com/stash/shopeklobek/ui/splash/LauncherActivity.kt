package com.stash.shopeklobek.ui.splash

import android.content.Intent
import android.os.Bundle
import android.os.PersistableBundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.paypal.checkout.PayPalCheckout
import com.paypal.checkout.config.CheckoutConfig
import com.paypal.checkout.config.Environment
import com.paypal.checkout.config.SettingsConfig
import com.paypal.checkout.createorder.CurrencyCode
import com.paypal.checkout.createorder.UserAction
import com.stash.shopeklobek.BuildConfig
import com.stash.shopeklobek.R
import com.stash.shopeklobek.ui.MainActivity
import com.stash.shopeklobek.utils.Constants.PAYPAL_CLIENT_ID
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class LauncherActivity : AppCompatActivity() {

    lateinit var splashViewModel: SplashViewModel

    override fun onCreate(savedInstanceState: Bundle?, persistentState: PersistableBundle?) {
        super.onCreate(savedInstanceState, persistentState)
        setContentView(R.layout.activity_checkout)
        Log.e("splashViewModel", "onCreate: splashViewModel" )
        this@LauncherActivity.startActivity(Intent(this@LauncherActivity, MainActivity::class.java))

//        lifecycleScope.launch {
////                splashViewModel.updateCurrency()
////                splashViewModel.updateLanguage()
////
////                PayPalCheckout.setConfig(CheckoutConfig(
////                    application = this@LauncherActivity.application,
////                    clientId = PAYPAL_CLIENT_ID,
////                    environment = Environment.SANDBOX,
////                    returnUrl = BuildConfig.APPLICATION_ID+"://paypalpay",
////                    currencyCode = CurrencyCode.USD,
////                    userAction = UserAction.PAY_NOW,
////                    settingsConfig = SettingsConfig(
////                        loggingEnabled = true
////                    )
////                ))
//
//            withContext(Dispatchers.Main){
//            }
//        }
    }

}