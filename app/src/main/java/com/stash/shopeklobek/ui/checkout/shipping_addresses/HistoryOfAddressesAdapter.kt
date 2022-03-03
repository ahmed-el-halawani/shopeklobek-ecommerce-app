package com.stash.shopeklobek.ui.checkout.shipping_addresses

import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.google.android.gms.maps.model.LatLng
import com.stash.shopeklobek.utils.OnlineMapImageHelper.getImageFromLatLon
import com.stash.shopeklobek.utils.customviews.AddressCardView

class HistoryOfAddressesAdapter : RecyclerView.Adapter<HistoryOfAddressesAdapter.ViewHolder>() {


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
        println(getImageFromLatLon(LatLng(30.0778, 31.2852)))

        holder.addressCardView.apply {
            title = "test location"
            address = "test Address"
//            setLocationImageResource(R.drawable.map)
            setImageFromUrl(getImageFromLatLon(LatLng(30.0778, 31.2852)))
            refresh()
        }
    }

    override fun getItemCount(): Int =
        10


//    // set listeners
//    fun setOnItemClickListener(onItemClickListener: ((WeatherLang) -> Unit)?) {
//        this.onItemClickListener = onItemClickListener
//    }

    // using DiffUtil to update our recycle
    // when update or change list of items
    private val differCallback = object : DiffUtil.ItemCallback<String>() {
        override fun areItemsTheSame(oldItem: String, newItem: String): Boolean = oldItem == newItem


        override fun areContentsTheSame(oldItem: String, newItem: String): Boolean =
            oldItem == newItem
    }

    val differ = AsyncListDiffer(this, differCallback)


    // private vars
//    private var onItemClickListener: ((WeatherLang) -> Unit)? = null

    data class ViewHolder(val addressCardView: AddressCardView) : RecyclerView.ViewHolder(addressCardView)


}