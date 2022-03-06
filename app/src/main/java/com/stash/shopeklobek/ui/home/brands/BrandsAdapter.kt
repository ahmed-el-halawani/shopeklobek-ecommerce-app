package com.stash.shopeklobek.ui.home.brands

import android.content.Context
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
import com.stash.shopeklobek.model.entities.SmartCollection

class BrandsAdapter(var listBrands: List<SmartCollection>, var context: Context,var fragment : Fragment) : RecyclerView.Adapter<BrandsAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view : View = LayoutInflater.from(parent.context).inflate(R.layout.item_brand,parent,false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        holder.brandTextView.text = listBrands[position].title
        Glide.with(holder.brandImageView.context).load(listBrands[position].image.src).into(holder.brandImageView)

        holder.brandConstrainLayout.setOnClickListener {
            val action = BrandsFragmentDirections.actionBrandsFragmentToVendorFragment(listBrands[position].title)
            fragment.findNavController().navigate(action)

        }
    }

    override fun getItemCount(): Int {
        return listBrands.size
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val brandImageView : ImageView
        get() = itemView.findViewById(R.id.brandImageView)

        val brandTextView : TextView
        get() = itemView.findViewById(R.id.categoryTitleTextView)

        val brandConstrainLayout : ConstraintLayout
        get() = itemView.findViewById(R.id.categoryConstrainLayout)

    }

}