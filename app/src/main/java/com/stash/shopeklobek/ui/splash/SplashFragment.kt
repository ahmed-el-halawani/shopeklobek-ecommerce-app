package com.stash.shopeklobek.ui.splash

import android.os.Bundle
import android.os.PersistableBundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.findNavController
import com.stash.shopeklobek.databinding.FragmentHomeBinding
import com.stash.shopeklobek.databinding.FragmentSplashBinding
import com.stash.shopeklobek.ui.BaseFragment

class SplashFragment : AppCompatActivity() {

    val splashViewModel by lazy {
        SplashViewModel.create(this)
    }

    override fun onCreate(savedInstanceState: Bundle?, persistentState: PersistableBundle?) {
        super.onCreate(savedInstanceState, persistentState)

    }

}