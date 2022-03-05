package com.stash.shopeklobek.ui.home.favorites

import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.stash.shopeklobek.databinding.FragmentFavoritesBinding
import com.stash.shopeklobek.ui.BaseFragment

class FavoritesFragment : BaseFragment<FragmentFavoritesBinding>(FragmentFavoritesBinding::inflate) {
    private lateinit var adapterFavorite: AdapterFavorite
    private lateinit var favoritesViewModel: FavoritesViewModel


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val favoritesModelFactory = FavoritesViewModel.Factory(requireActivity().application)
        favoritesViewModel = ViewModelProvider(this, favoritesModelFactory)[FavoritesViewModel::class.java]

        adapterFavorite = AdapterFavorite(ArrayList(),favoritesViewModel)
        binding.reFavorite.layoutManager = GridLayoutManager(context, 2)
        binding?.reFavorite?.adapter = adapterFavorite
        favoritesViewModel.getFavorites()
      // get data from room
        favoritesViewModel.favorites.observe(viewLifecycleOwner, Observer {
            if (it!=null){
            adapterFavorite.setFavorite(it)
            binding.ivEmptyFavorite.visibility=View.GONE
            }else{
                binding.ivEmptyFavorite.visibility=View.VISIBLE
            }


        })


       /* var arrayList=ArrayList<ModelFavorite>()
        arrayList.add(ModelFavorite("Sneakers","https://assets.brantu.com/product/3987777-71619/1000x1500/1640123440076-3987777-71619-0-3.jpeg","300.30$"))
        arrayList.add(ModelFavorite("Ankle Boot","https://assets.brantu.com/product/7783897-42287/1000x1500/1637228503727-7783897-42287-0-3.jpeg","300.30$"))
        arrayList.add(ModelFavorite("Ankle Boot","https://assets.brantu.com/product/p9775715/1000x1500/natural-leather-ankle-boot-coffee-1634739331625-3.jpeg","300.30$"))
        arrayList.add(ModelFavorite("Ankle Boot","https://assets.brantu.com/product/4924645-512359/1000x1500/1642499781286-4924645-512359-2-3.jpeg","300.30$"))
        arrayList.add(ModelFavorite("Sneakers","https://assets.brantu.com/product/6634734-512359/1000x1500/1640122662425-6634734-512359-0-3.jpeg","300.30$"))
        arrayList.add(ModelFavorite("Sneakers","https://assets.brantu.com/product/5283975-512359/1000x1500/1640123209608-5283975-512359-0-3.jpeg","449 .30$"))
        arrayList.add(ModelFavorite("Sneakers","https://assets.brantu.com/product/3987777-71619/1000x1500/1640123440076-3987777-71619-0-3.jpeg","300.30$"))
        arrayList.add(ModelFavorite("shoes","https://assets.brantu.com/product/3987777-71619/1000x1500/1640123440076-3987777-71619-0-3.jpeg","300.30$"))
        arrayList.add(ModelFavorite("Ankle Boot","https://assets.brantu.com/product/3987777-71619/1000x1500/1640123440076-3987777-71619-0-3.jpeg","300.30$"))
        arrayList.add(ModelFavorite("Ankle Boot","https://assets.brantu.com/product/7783897-42287/1000x1500/1637228503727-7783897-42287-0-3.jpeg","300.30$"))
        arrayList.add(ModelFavorite("Ankle Boot","https://assets.brantu.com/product/p9775715/1000x1500/natural-leather-ankle-boot-coffee-1634739331625-3.jpeg","300.30$"))
        arrayList.add(ModelFavorite("Ankle Boot","https://assets.brantu.com/product/4924645-512359/1000x1500/1642499781286-4924645-512359-2-3.jpeg","300.30$"))
        arrayList.add(ModelFavorite("Sneakers","https://assets.brantu.com/product/6634734-512359/1000x1500/1640122662425-6634734-512359-0-3.jpeg","300.30$"))
        arrayList.add(ModelFavorite("Sneakers","https://assets.brantu.com/product/5283975-512359/1000x1500/1640123209608-5283975-512359-0-3.jpeg","449 .30$"))
        arrayList.add(ModelFavorite("Sneakers","https://assets.brantu.com/product/3987777-71619/1000x1500/1640123440076-3987777-71619-0-3.jpeg","300.30$"))
        arrayList.add(ModelFavorite("shoes","https://assets.brantu.com/product/3987777-71619/1000x1500/1640123440076-3987777-71619-0-3.jpeg","300.30$"))

*/


        super.onViewCreated(view, savedInstanceState)

    }
}