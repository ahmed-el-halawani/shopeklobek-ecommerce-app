package com.stash.shopeklobek.ui.checkout.search_places

import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.google.android.gms.maps.model.LatLng
import com.stash.shopeklobek.model.entities.autocomplete_places_2.Feature
import com.stash.shopeklobek.model.entities.places.PlacesResultItem
import com.stash.shopeklobek.utils.ViewHelpers
import com.stash.shopeklobek.utils.customviews.AddressCardView

class SearchedAddressesAdapter : RecyclerView.Adapter<SearchedAddressesAdapter.ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
        ViewHolder(
            AddressCardView(parent.context).apply {
                layoutParams = ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT
                )
            }
        )

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val addressData = differ.currentList[position]

        holder.addressCardView.apply {
            title = addressData.properties.country
            address = addressData.properties.generateAddress()
            addressData.properties.getLatLng()?.let { setImageFromUrl(it) }
            setOnClickListener { onItemClickListener?.invoke(addressData) }
            refresh()
        }
    }

    override fun getItemCount(): Int =
        differ.currentList.size


    //    // set listeners
    fun setOnItemClickListener(onItemClickListener: ((Feature) -> Unit)?) {
        this.onItemClickListener = onItemClickListener
    }

    // using DiffUtil to update our recycle
    // when update or change list of items
    private val differCallback = object : DiffUtil.ItemCallback<Feature>() {
        override fun areItemsTheSame(oldItem: Feature, newItem: Feature): Boolean =
            oldItem.properties.place_id == newItem.properties.place_id

        override fun areContentsTheSame(oldItem: Feature, newItem: Feature): Boolean =
            oldItem == newItem
    }

    val differ = AsyncListDiffer(this, differCallback)


    // private vars
    private var onItemClickListener: ((Feature) -> Unit)? = null

    data class ViewHolder(val addressCardView: AddressCardView) : RecyclerView.ViewHolder(addressCardView)


}