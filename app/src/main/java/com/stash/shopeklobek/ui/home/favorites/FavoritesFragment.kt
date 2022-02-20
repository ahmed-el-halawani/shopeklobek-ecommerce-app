package com.stash.shopeklobek.ui.home.favorites

import android.os.Bundle
import android.view.View
import com.stash.shopeklobek.databinding.FragmentFavoritesBinding
import com.stash.shopeklobek.databinding.FragmentHomeBinding
import com.stash.shopeklobek.ui.BaseFragment

class FavoritesFragment : BaseFragment<FragmentFavoritesBinding>(FragmentFavoritesBinding::inflate) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        parentFragment
    }
}