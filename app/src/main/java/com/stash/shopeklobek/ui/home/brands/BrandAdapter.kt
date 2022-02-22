package com.stash.shopeklobek.ui.home.brands

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.stash.shopeklobek.R

class BrandAdapter(var context:Context ) : RecyclerView.Adapter<BrandAdapter.ViewHolder>()  {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_brand,parent,false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        TODO("Not yet implemented")
    }

    override fun getItemCount(): Int {
        TODO("Not yet implemented")
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val categoryTextView : TextView
            get() = itemView.findViewById(R.id.categoryTextView)

        val categoryImage : ImageView
        get() =  itemView.findViewById(R.id.categoryImage)

    }
}