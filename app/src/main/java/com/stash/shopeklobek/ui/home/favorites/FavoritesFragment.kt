package com.stash.shopeklobek.ui.home.favorites

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.stash.shopeklobek.databinding.FragmentFavoritesBinding
import com.stash.shopeklobek.model.utils.Either
import com.stash.shopeklobek.ui.BaseFragment

class FavoritesFragment :
    BaseFragment<FragmentFavoritesBinding>(FragmentFavoritesBinding::inflate) {
    private lateinit var adapterFavorite: AdapterFavorite
    private val favoritesViewModel: FavoritesViewModel by activityViewModels()
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {


        adapterFavorite = AdapterFavorite(ArrayList(), favoritesViewModel)
        binding.reFavorite.layoutManager = GridLayoutManager(context, 2)
        binding?.reFavorite?.adapter = adapterFavorite

        // get data from room
        favoritesViewModel.repo.getSettingsLiveData().observe(viewLifecycleOwner) {
            if (it.customer == null) {
                binding.ivEmptyFavorite.visibility = View.VISIBLE
                binding.tvLoginFavorite.visibility = View.VISIBLE
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
                                Toast.makeText(activity," "+it.size, Toast.LENGTH_LONG).show()
                             } else {
                                binding.ivEmptyFavorite.visibility = View.VISIBLE
                                Log.d("onViewCreated", "nullll")
                                Toast.makeText(activity, "no data", Toast.LENGTH_LONG).show()
                            }

                        }

                    }
                }

            }
        }




        super.onViewCreated(view, savedInstanceState)

    }

}