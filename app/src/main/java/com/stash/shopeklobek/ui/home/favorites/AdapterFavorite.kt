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
import com.bumptech.glide.Glide
import com.stash.shopeklobek.R
import com.stash.shopeklobek.model.entities.room.RoomFavorite

class AdapterFavorite(
    var listFavorites:  List<RoomFavorite>,
    var favoritesViewModel: FavoritesViewModel?
) :
    RecyclerView.Adapter<AdapterFavorite.ViewHolder>() {


    inner class ViewHolder(val view: View) : RecyclerView.ViewHolder(view) {

        val tvTitle: TextView
            get() = view.findViewById(R.id.tv_title_item)
        val tvPrice: TextView
            get() = view.findViewById(R.id.tv_price_item)
        val imageItem: ImageView
            get() = view.findViewById(R.id.image_product_item)
        val ivDeleteFavorite: ImageView
            get() = view.findViewById(R.id.iv_delete_favorite)


    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val viwe: View =
            LayoutInflater.from(parent.context).inflate(R.layout.item_product, parent, false)
        return ViewHolder(viwe)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        Glide.with(holder.imageItem.context).load(listFavorites.get(position).product.image.src)
            .into(holder.imageItem)
        holder.tvTitle.text = listFavorites.get(position).product.title
        holder.tvPrice.text = listFavorites.get(position).product.variants[0]?.price

        holder.ivDeleteFavorite.setOnClickListener {

            AlertDialog.Builder(holder.imageItem.context).apply {
                setNegativeButton("No") { d, i ->
                    d.dismiss()
                }
                setPositiveButton("yes") { d, i ->
                    favoritesViewModel?.deleteFavorite(listFavorites.get(position).id)
                    Toast.makeText(holder.imageItem.context,holder.imageItem.context.getString(R.string.product_deleted),Toast.LENGTH_LONG).show()
                    notifyDataSetChanged()
                    d.dismiss()
                 }

                setTitle(holder.imageItem.context.getString(R.string.do_u_want_remove_product))
            }.create().show()



        }


    }

    fun setFavorite(favorite: List<RoomFavorite>) {
        if (!favorite.isEmpty()){
        this.listFavorites = favorite
        notifyDataSetChanged()
    }else
            notifyDataSetChanged()

    }


    override fun getItemCount(): Int = listFavorites.size
}