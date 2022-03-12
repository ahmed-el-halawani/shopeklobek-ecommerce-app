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
import kotlinx.coroutines.*

class LauncherSActivity : AppCompatActivity() {
    lateinit var splashViewModel: SplashViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_launcher_sactivity)

        splashViewModel = SplashViewModel.create(this)

        if(savedInstanceState==null){
            lifecycleScope.launch {
                delay(1000)
                withContext(Dispatchers.Main){
                    startActivity(Intent(this@LauncherSActivity, MainActivity::class.java))
                    finishAffinity()
                }
            }
        }

    }
}