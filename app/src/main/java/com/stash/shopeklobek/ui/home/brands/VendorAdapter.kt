package com.stash.shopeklobek.ui.home.brands

import android.content.Context
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

class VendorAdapter(var listProducts: List<Products>, var addToFavorite: (Products) -> Unit ) : RecyclerView.Adapter<VendorAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view : View = LayoutInflater.from(parent.context).inflate(R.layout.item_category,parent,false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.categoryTitleTextView.text = listProducts[position].variants[listProducts[position].variants.lastIndex]?.price
        Glide.with(holder.categoryImageView.context).load(listProducts[position].image.src).into(holder.categoryImageView)
        holder.categoryFavoriteImageView.setOnClickListener {
            addToFavorite(listProducts[position])
            holder.categoryFavoriteImageView.setImageResource(R.drawable.ic_baseline_favorite_24_red)
        }
        holder.categoryConstrainLayout.setOnClickListener {
            //TODO
            it.findNavController().navigate(R.id.action_vendorFragment_to_productDetailsFragment)
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