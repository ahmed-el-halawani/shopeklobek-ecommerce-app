package com.stash.shopeklobek.ui.home.categories

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.stash.shopeklobek.R
import com.stash.shopeklobek.databinding.FragmentCategoriesBinding
import com.stash.shopeklobek.model.entities.Products
import com.stash.shopeklobek.model.utils.Either
import com.stash.shopeklobek.model.utils.RepoErrors
import com.stash.shopeklobek.ui.BaseFragment
import com.stash.shopeklobek.ui.home.brands.VendorAdapter
import com.stash.shopeklobek.utils.Constants.TAG
import com.stash.shopeklobek.utils.observeOnce

class CategoriesFragment : BaseFragment<FragmentCategoriesBinding>(FragmentCategoriesBinding::inflate) {

    private lateinit var categoryAdapter: CategoryAdapter
    private val recyclerView: RecyclerView by lazy { binding.categoryRecyclerView }
    private val categoriesViewModel: CategoriesViewModel by activityViewModels()
    private val hashMap:HashMap<String,Long> = HashMap()
    var searching : MutableLiveData<String> = MutableLiveData()




    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.filterLayout.setOnClickListener {
            val filterBottomSheet = FilterBottomSheet(hashMap)
            filterBottomSheet.show(parentFragmentManager,"TAG")
        }

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        categoriesViewModel.category.observe(viewLifecycleOwner, Observer {
            when(it) {
                is Either.Success -> {
                    for (i in 0 ..((it.data.collections?.size)?.minus(1) ?: 0)){
                        hashMap[it.data.collections?.get(i)?.collectionsHandle!!] = it.data.collections[i].collectionsId!!
                    }
                }
                is Either.Error -> when (it.errorCode) {
                    RepoErrors.NoInternetConnection -> Toast.makeText(requireContext(), "No Connection", Toast.LENGTH_SHORT).show()
                    RepoErrors.ServerError -> Toast.makeText(requireContext(), "Error!", Toast.LENGTH_SHORT).show()
                }
            }
        })
        return super.onCreateView(inflater, container, savedInstanceState)
    }

    override fun onResume() {
        super.onResume()
        recyclerView.layoutManager = GridLayoutManager(requireContext(),2, RecyclerView.VERTICAL,false)

        categoriesViewModel.getFavorites()
        categoriesViewModel.products.observe(viewLifecycleOwner, Observer {
            when(it) {
                is Either.Success -> {
                    checkFavoriteList(it.data.product)
                }
                is Either.Error -> when (it.errorCode) {
                    RepoErrors.NoInternetConnection -> Toast.makeText(requireContext(), "No Connection", Toast.LENGTH_SHORT).show()
                    RepoErrors.ServerError -> Toast.makeText(requireContext(), "Error!", Toast.LENGTH_SHORT).show()
                }
            }
        })

        binding.searchCategoryTextView.addTextChangedListener {
            searching.value = binding.searchCategoryTextView.text.toString()
        }

        searching.observe(viewLifecycleOwner, Observer { it2 ->
            categoriesViewModel.products.observe(viewLifecycleOwner, Observer { it1 ->
                when(it1) {
                    is Either.Success -> {
                        if(it2 == null){
                            checkFavoriteList(it1.data.product)
                        }else {
                            var list: MutableList<Products> = mutableListOf()
                            for (i in 0..it1.data.product.size.minus(1)) {
                                var string = binding.searchCategoryTextView.text
                                if (it1.data.product[i].title!!.contains(string, ignoreCase = true)) {
                                    list.add(it1.data.product[i])
                                }
                            }
                            checkFavoriteList(list)
                        }
                    }
                    is Either.Error -> when (it1.errorCode) {
                        RepoErrors.NoInternetConnection -> Toast.makeText(requireContext(), "No Connection", Toast.LENGTH_SHORT).show()
                        RepoErrors.ServerError -> Toast.makeText(requireContext(), "Error!", Toast.LENGTH_SHORT).show()
                    }
                }
            })
        })

        categoriesViewModel.firstFilter.observe(viewLifecycleOwner, Observer {
            when(it){
                "women" -> binding.filterTextView.text = resources.getString(R.string.women)
                "men" -> binding.filterTextView.text = resources.getString(R.string.men)
                "kid" -> binding.filterTextView.text = resources.getString(R.string.kids)
                "sale" -> binding.filterTextView.text = resources.getString(R.string.sale)
                else -> binding.filterTextView.text = resources.getString(R.string.none)
            }
        })

        categoriesViewModel.secondFilter.observe(viewLifecycleOwner, Observer {
            when(it){
                "all" -> binding.filterTextView2.text = resources.getString(R.string.all)
                "SHOES" -> binding.filterTextView2.text = resources.getString(R.string.shoes)
                "ACCESSORIES" -> binding.filterTextView2.text = resources.getString(R.string.accessories)
                "T-SHIRTS" -> binding.filterTextView2.text = resources.getString(R.string.clothes)
                else -> binding.filterTextView2.text = resources.getString(R.string.none)
            }
        })
    }

    fun checkFavoriteList(listOfProducts : List<Products> ){
        when(val favorite = categoriesViewModel.getFavorites()){
            is Either.Error -> {
                categoryAdapter = CategoryAdapter(listOfProducts,categoriesViewModel::addToFavorite,categoriesViewModel::deleteFavorite,emptyList())
                recyclerView.adapter = categoryAdapter}
            is Either.Success -> {
                favorite.data.observeOnce(viewLifecycleOwner){
                    categoryAdapter = CategoryAdapter(listOfProducts,categoriesViewModel::addToFavorite,categoriesViewModel::deleteFavorite, it)
                    recyclerView.adapter = categoryAdapter
                }
            }
        }
    }
}