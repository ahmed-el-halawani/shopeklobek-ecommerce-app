package com.stash.shopeklobek.ui.splash

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
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
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class LauncherSActivity : AppCompatActivity() {
    lateinit var splashViewModel: SplashViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_launcher_sactivity)

        splashViewModel = SplashViewModel.create(this)
        Log.e("splashViewModel", "onCreate: splashViewModel" )

        if(savedInstanceState==null){
            lifecycleScope.launch {
                splashViewModel.updateCurrency()

                GlobalScope.launch {
                    PayPalCheckout.setConfig(
                        CheckoutConfig(
                            application = application,
                            clientId = PAYPAL_CLIENT_ID,
                            environment = Environment.SANDBOX,
                            returnUrl = BuildConfig.APPLICATION_ID+"://paypalpay",
                            currencyCode = CurrencyCode.USD,
                            userAction = UserAction.PAY_NOW,
                            settingsConfig = SettingsConfig(
                                loggingEnabled = true
                            )
                        )
                    )
                }

                withContext(Dispatchers.Main){
                    startActivity(Intent(this@LauncherSActivity, MainActivity::class.java))
                    finishAffinity()
                }
            }
        }

    }
}