package com.stash.shopeklobek.ui.home.brands

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Adapter
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.stash.shopeklobek.R
import com.stash.shopeklobek.model.ModelFavorite

class BrandsAdapter(var listFavorites: ArrayList<ModelFavorite>) : RecyclerView.Adapter<BrandsAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view : View = LayoutInflater.from(parent.context).inflate(R.layout.item_brand,parent,false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        holder.brandTextView.text = listFavorites[position].title
        Glide.with(holder.brandImageView.context).load(listFavorites[position].image).into(holder.brandImageView)
    }

    override fun getItemCount(): Int {
        return listFavorites.size
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val brandImageView : ImageView
        get() = itemView.findViewById(R.id.brandImageView)

        val brandTextView : TextView
        get() = itemView.findViewById(R.id.brandTextView)

        val brandConstrainLayout : ConstraintLayout
        get() = itemView.findViewById(R.id.brandConstrainLayout)

    }

}