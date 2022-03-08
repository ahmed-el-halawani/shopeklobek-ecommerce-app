package com.stash.shopeklobek.ui.checkout.shipping_addresses

import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.stash.shopeklobek.model.entities.Address
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
        val addressData = differ.currentList[position]

        holder.addressCardView.apply {
            title = addressData.city
            address = addressData.generateAddressLine()
            setOnClickListener { onItemClickListener?.invoke(addressData) }
            refresh()

//            setImageFromUrl(getImageFromLatLon(LatLng(30.0778, 31.2852)))
            //setLocationImageResource(R.drawable.map)
        }
    }

    override fun getItemCount(): Int =
        differ.currentList.size


    //    // set listeners
    fun setOnItemClickListener(onItemClickListener: ((Address) -> Unit)?) {
        this.onItemClickListener = onItemClickListener
    }

    // using DiffUtil to update our recycle
    // when update or change list of items
    private val differCallback = object : DiffUtil.ItemCallback<Address>() {
        override fun areItemsTheSame(oldItem: Address, newItem: Address): Boolean =
            oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: Address, newItem: Address): Boolean =
            oldItem == newItem
    }

    val differ = AsyncListDiffer(this, differCallback)


    // private vars
    private var onItemClickListener: ((Address) -> Unit)? = null

    data class ViewHolder(val addressCardView: AddressCardView) : RecyclerView.ViewHolder(addressCardView)


}