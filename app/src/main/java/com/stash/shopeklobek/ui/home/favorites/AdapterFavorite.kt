package com.stash.shopeklobek.ui.home.favorites

import android.app.AlertDialog
import android.content.DialogInterface
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.bumptech.glide.Glide
import com.stash.shopeklobek.R
import com.stash.shopeklobek.model.ModelFavorite
import com.stash.shopeklobek.model.entities.room.RoomFavorite
import java.lang.String

class AdapterFavorite(var listFavorites:  List<RoomFavorite>) :
    RecyclerView.Adapter<AdapterFavorite.ViewHolder>() {


    inner class ViewHolder(val view: View) : RecyclerView.ViewHolder(view) {

        val tvTitle: TextView
            get() = view.findViewById(R.id.tv_title_item)
        val tvPrice: TextView
            get() = view.findViewById(R.id.tv_price_item)
        val imageItem: ImageView
            get() = view.findViewById(R.id.image_product_item)


    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val viwe: View =
            LayoutInflater.from(parent.context).inflate(R.layout.item_product, parent, false)
        return ViewHolder(viwe)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        Glide.with(holder.imageItem.context).load(listFavorites.get(position).product.image)
            .into(holder.imageItem)
        holder.tvTitle.text = listFavorites.get(position).product.title
        holder.tvPrice.text = listFavorites.get(position).product.variants[0]?.price


    }

    fun setFavorite(favorite: List<RoomFavorite>) {
        this.listFavorites = favorite
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int = listFavorites.size
}