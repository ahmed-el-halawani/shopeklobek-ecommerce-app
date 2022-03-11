package com.stash.shopeklobek.ui.home.brands

import android.annotation.SuppressLint
import android.content.res.Resources
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.stash.shopeklobek.R
import com.stash.shopeklobek.model.entities.Products
import com.stash.shopeklobek.model.entities.room.RoomFavorite
import com.stash.shopeklobek.utils.Constants.TAG
import com.stash.shopeklobek.utils.toCurrency

class VendorAdapter(var listProducts: List<Products>, var addToFavorite: (Products) -> Unit, var deleteFavorite : (Products) -> Unit
                    ,var listFavorites : List<RoomFavorite>) : RecyclerView.Adapter<VendorAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view : View = LayoutInflater.from(parent.context).inflate(R.layout.item_category,parent,false)
        return ViewHolder(view)
    }

    @SuppressLint("UseCompatLoadingForDrawables", "ResourceType")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.categoryTitleTextView.text = listProducts[position].title
        holder.categoryPriceTextView.text = listProducts[position].variants[listProducts[position].variants.lastIndex]?.price?.toCurrency(holder.itemView.context)
        Glide.with(holder.categoryImageView.context).load(listProducts[position].image.src).into(holder.categoryImageView)

        for ( i in 0 .. listFavorites.size.minus(1)) {
            if (listProducts[position].productId == listFavorites[i].product.productId) {
                holder.categoryFavoriteImageView.setImageResource(R.drawable.ic_baseline_favorite_24_red)
                holder.categoryFavoriteImageView.tag="favorite"
            }
        }

        holder.categoryFavoriteImageView.setOnClickListener {
            if(holder.categoryFavoriteImageView.tag == "favorite"){
                deleteFavorite(listProducts[position])
                holder.categoryFavoriteImageView.setImageResource(R.drawable.ic_baseline_favorite)
                holder.categoryFavoriteImageView.tag ="unFavorite"
            }else{
                addToFavorite(listProducts[position])
                holder.categoryFavoriteImageView.setImageResource(R.drawable.ic_baseline_favorite_24_red)
                holder.categoryFavoriteImageView.tag="favorite"
            }
        }


        holder.categoryConstrainLayout.setOnClickListener {
            val action = VendorFragmentDirections.actionVendorFragmentToProductDetailsFragment(listProducts[position])
            it.findNavController().navigate(action)
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

        val categoryPriceTextView : TextView
            get() = itemView.findViewById(R.id.categoryPriceTextView)

        val categoryTitleTextView : TextView
            get() = itemView.findViewById(R.id.categoryTitleTextView)

        val categoryConstrainLayout : ConstraintLayout
            get() = itemView.findViewById(R.id.categoryConstrainLayout)




    }
}