package com.stash.shopeklobek.ui.home.brands

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.denzcoskun.imageslider.ImageSlider
import com.denzcoskun.imageslider.constants.ScaleTypes
import com.denzcoskun.imageslider.interfaces.ItemClickListener
import com.denzcoskun.imageslider.models.SlideModel
import com.stash.shopeklobek.R
import com.stash.shopeklobek.databinding.FragmentBrandsBinding
import com.stash.shopeklobek.model.utils.Either
import com.stash.shopeklobek.model.utils.RepoErrors
import com.stash.shopeklobek.ui.BaseFragment
import com.stash.shopeklobek.utils.Constants.TAG

class BrandsFragment : BaseFragment<FragmentBrandsBinding>(FragmentBrandsBinding::inflate) {

    private lateinit var brandsAdapter: BrandsAdapter
    private val recyclerView: RecyclerView by lazy { binding.brandRecyclerView }
    private val imageSlider : ImageSlider by lazy { binding.imageSlider }
    private lateinit var brandsViewModel: BrandsViewModel


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val brandsViewModelFactory = BrandsViewModel.Factory(requireActivity().application)
        brandsViewModel = ViewModelProvider(this, brandsViewModelFactory)[BrandsViewModel::class.java]

        val imageList = ArrayList<SlideModel>()
        val saleDiscountCode = ArrayList<String>()

        brandsViewModel.discounts.observe(viewLifecycleOwner, Observer {
            when (it) {
                is Either.Success -> {
                    for (i in 0..it.data.discount?.size?.minus(1)!!) {
                        saleDiscountCode.add(it.data.discount[i].title.toString())
                    }
                }
            }
        })

        imageList.add(SlideModel(R.drawable.sale))
        imageSlider.setImageList(imageList, ScaleTypes.CENTER_CROP)
        Log.i(TAG, "onViewCreated: "+imageList.size)
        Log.i(TAG, "onViewCreated: "+saleDiscountCode.size)


        imageSlider.setItemClickListener(object : ItemClickListener {
            override fun onItemSelected(position: Int) {
                Log.i(TAG, "onItemSelected: ")
                val myClipboard = requireContext().getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
                val myClip: ClipData = ClipData.newPlainText("Label","Hi")
                myClipboard.setPrimaryClip(myClip)
                /*brandsViewModel.discounts.observe(viewLifecycleOwner, Observer {
                    when (it) {
                        is Either.Success -> {
                            val myClipboard = requireContext().getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
                            val myClip: ClipData = ClipData.newPlainText("Label","Hi")
                            Log.i(TAG, "onItemSelected: "+ it.data.discount?.get(position)?.title)
                            myClipboard.setPrimaryClip(myClip)
                        }
                    }
                })*/
            }
        })

        brandsViewModel.brands.observe(viewLifecycleOwner, Observer {
            when (it) {
                is Either.Success -> {
                    brandsAdapter = BrandsAdapter(it.data.smart_collections!!)
                    recyclerView.layoutManager = GridLayoutManager(requireContext(),2,RecyclerView.VERTICAL,false)
                    recyclerView.adapter = brandsAdapter
                }
                is Either.Error -> when (it.errorCode) {
                    RepoErrors.NoInternetConnection -> Toast.makeText(requireContext(), "No Connection", Toast.LENGTH_SHORT)
                        .show()
                    RepoErrors.ServerError -> Toast.makeText(requireContext(), "Error!", Toast.LENGTH_SHORT).show()
                    RepoErrors.EmptyBody -> Toast.makeText(requireContext(), "empty body!", Toast.LENGTH_SHORT).show()
                }
            }
        })


        /*brandsViewModel.loadingLiveData.observe(viewLifecycleOwner, Observer {
            when(it){
                true ->{
                    showLoading()
                }
                false -> {
                    hideLoading()
                }
            }
        })
    }

        })*/
    }
}