package com.stash.shopeklobek.ui.home.categories

import android.app.Application
import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.stash.shopeklobek.R
import com.stash.shopeklobek.model.api.ShopifyApi
import com.stash.shopeklobek.model.entities.Products
import com.stash.shopeklobek.model.repositories.ProductRepo
import com.stash.shopeklobek.model.shareprefrances.SettingsPreferences
import com.stash.shopeklobek.utils.Constants.TAG
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class CategoryAdapter(var listProducts: List<Products>, var context: Context, var fragment: Fragment) : RecyclerView.Adapter<CategoryAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view : View = LayoutInflater.from(parent.context).inflate(R.layout.item_category,parent,false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.categoryTitleTextView.text = listProducts[position].variants[listProducts[position].variants.lastIndex]?.price
        val application = holder.itemView.context.applicationContext as Application
        val repo = ProductRepo(ShopifyApi.api, SettingsPreferences.getInstance(application),application)
        val product = listProducts[position]
        Glide.with(holder.categoryImageView.context).load(listProducts[position].image.src).into(holder.categoryImageView)
        holder.categoryFavoriteImageView.setOnClickListener {
            //TODO()
            CoroutineScope(Dispatchers.IO).launch {
                Log.e("onBindViewHolder", "onBindViewHolder: "+repo.addToCart(product), )
            }
            Log.i(TAG, "onBindViewHolder: on click favorite")
            holder.categoryFavoriteImageView.setImageResource(R.drawable.ic_baseline_favorite_24_red)
        }
        holder.categoryConstrainLayout.setOnClickListener { 
            //TODO
            Log.i(TAG, "onBindViewHolder: "+listProducts[position].productId)
            fragment.findNavController().navigate(R.id.product_details_fragment)
        }
        
    }

    override fun getItemCount(): Int {
        return listProducts.size
    }

    class ViewHolder(item : View) : RecyclerView.ViewHolder(item) {

        val categoryImageView : ImageView
            get() = itemView.findViewById(R.id.categoryImageView)

        val categoryFavoriteImageView : ImageView
            get() = itemView.findViewById(R.id.categoryFavoriteImageView)

        val categoryTitleTextView : TextView
            get() = itemView.findViewById(R.id.categoryTitleTextView)
        
        val categoryConstrainLayout : ConstraintLayout
        get() = itemView.findViewById(R.id.categoryConstrainLayout)




    }
}