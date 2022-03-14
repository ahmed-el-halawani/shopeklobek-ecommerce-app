package com.stash.shopeklobek.ui.home.favorites

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.stash.shopeklobek.R
import com.stash.shopeklobek.databinding.FragmentFavoritesBinding
import com.stash.shopeklobek.model.utils.Either
import com.stash.shopeklobek.ui.BaseFragment
import com.stash.shopeklobek.utils.Constants

class FavoritesFragment :
    BaseFragment<FragmentFavoritesBinding>(FragmentFavoritesBinding::inflate) {
    private lateinit var adapterFavorite: AdapterFavorite
    private val favoritesViewModel: FavoritesViewModel by activityViewModels()
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        activity?.let {
            val sharedPreferences: SharedPreferences = it.getSharedPreferences(
                "sharedPrefFile",
                Context.MODE_PRIVATE
            )

            val editor: SharedPreferences.Editor = sharedPreferences.edit()
            editor.putInt(Constants.ONCLICK_PROFILE, 2)
            editor.apply()
            editor.commit()

        }
        adapterFavorite = AdapterFavorite(ArrayList(), favoritesViewModel)
        binding.reFavorite.layoutManager = GridLayoutManager(context, 2)
        binding?.reFavorite?.adapter = adapterFavorite

        // get data from room
        favoritesViewModel.repo.getSettingsLiveData().observe(viewLifecycleOwner) {
            if (it.customer == null) {
                binding.ivEmptyFavorite.visibility = View.VISIBLE
                binding.tvLoginFavorite.visibility = View.VISIBLE
                findNavController().navigate(R.id.action_nav_not_login)

            } else {
                binding.tvLoginFavorite.visibility = View.GONE
                binding.ivEmptyFavorite.visibility = View.GONE
                when( val res=favoritesViewModel.getFavorites())
                {
                    is Either.Error -> {
                        TODO()
                    }
                    is Either.Success -> {
                        res.data.observe(viewLifecycleOwner){
                            if (!it.isEmpty()) {
                                adapterFavorite.setFavorite(it)
                                binding.ivEmptyFavorite.visibility = View.GONE
                              } else {
                                binding.ivEmptyFavorite.visibility = View.VISIBLE
                                Log.d("onViewCreated", "nullll")

                            }

                        }

                    }
                }

            }
        }




        super.onViewCreated(view, savedInstanceState)

    }

}