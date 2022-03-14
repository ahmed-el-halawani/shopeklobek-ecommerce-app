package com.stash.shopeklobek.ui.splash

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import com.orhanobut.hawk.Hawk
import com.stash.shopeklobek.R
import com.stash.shopeklobek.ui.MainActivity
import com.stash.shopeklobek.utils.ViewHelpers.setDarkMode
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext

class LauncherSActivity : AppCompatActivity() {
    lateinit var splashViewModel: SplashViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_launcher_sactivity)

        splashViewModel = SplashViewModel.create(this)




        splashViewModel.initIt {
            if (Hawk.get<Boolean?>("darkMode") == null)
                when (AppCompatDelegate.getDefaultNightMode()) {

                    AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM -> {
                        Hawk.delete("darkMode")
                    }
                    AppCompatDelegate.MODE_NIGHT_NO -> {
                        Hawk.put("darkMode", false)
                    }

                    AppCompatDelegate.MODE_NIGHT_YES -> {
                        Hawk.put("darkMode", true)
                    }
                    else -> Hawk.delete("darkMode")

                }
            else
                setDarkMode()
            
            delay(1000)
            withContext(Dispatchers.Main) {
                startActivity(Intent(this@LauncherSActivity, MainActivity::class.java))
                finishAffinity()
            }
        }

    }
}