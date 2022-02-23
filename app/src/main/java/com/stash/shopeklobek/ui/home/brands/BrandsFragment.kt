package com.stash.shopeklobek.ui.home.brands

import android.os.Bundle
import android.view.View
import android.widget.GridLayout
import android.widget.Toast
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.denzcoskun.imageslider.constants.ScaleTypes
import com.denzcoskun.imageslider.interfaces.ItemClickListener
import com.denzcoskun.imageslider.models.SlideModel
import com.stash.shopeklobek.databinding.FragmentBrandsBinding
import com.stash.shopeklobek.databinding.FragmentHomeBinding
import com.stash.shopeklobek.model.ModelFavorite
import com.stash.shopeklobek.ui.BaseFragment

class BrandsFragment : BaseFragment<FragmentBrandsBinding>(FragmentBrandsBinding::inflate) {

    private val imageList = ArrayList<SlideModel>() // Create image list
    private lateinit var brandsAdapter: BrandsAdapter
    private lateinit var recyclerView: RecyclerView


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        imageList.add(SlideModel("https://bit.ly/2YoJ77H", "The animal population decreased by 58 percent in 42 years."))
        imageList.add(SlideModel("https://bit.ly/2BteuF2", "Elephants and tigers may become extinct."))
        imageList.add(SlideModel("https://bit.ly/3fLJf72", "And people do that."))

        val imageSlider = binding.imageSlider
        imageSlider.setImageList(imageList)
        imageSlider.setImageList(imageList, ScaleTypes.CENTER_CROP)

        imageSlider.setItemClickListener(object : ItemClickListener {
            override fun onItemSelected(position: Int) {
                Toast.makeText(requireContext(),"Clicked on Slider",Toast.LENGTH_SHORT).show()
            }
        })

        var arrayList=ArrayList<ModelFavorite>()
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

        recyclerView = binding.brandRecyclerView
        brandsAdapter = BrandsAdapter(arrayList)

        recyclerView.layoutManager = GridLayoutManager(requireContext(),2,RecyclerView.VERTICAL,false)
        recyclerView.adapter = brandsAdapter



    }
}