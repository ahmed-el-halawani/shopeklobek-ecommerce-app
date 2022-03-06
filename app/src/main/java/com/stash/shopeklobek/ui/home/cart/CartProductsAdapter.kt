package com.stash.shopeklobek.ui.home.cart

import android.provider.Settings
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.stash.shopeklobek.databinding.ItemCartProductBinding
import com.stash.shopeklobek.model.entities.room.RoomCart
import com.stash.shopeklobek.utils.CurrencyUtil.convertCurrency

class CartProductsAdapter : RecyclerView.Adapter<CartProductsAdapter.ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
        ViewHolder(
            ItemCartProductBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        differ.currentList[position].also { rc ->
            holder.binding.run {

                Glide.with(root).load(rc.product.image.src).into(ivProductImage)

                tvPrice.text = convertCurrency(rc.variant()?.price)

                tvCurrencyUnit.text = ""

                tvCounter.text = rc.count.toString()

                tvProductTitle.text = rc.product.title
                tvQuality.text = rc.product.productType

                btnAdd.setOnClickListener {
                    onIncrementClickListener?.invoke(rc)
                }

                btnRemove.setOnClickListener {
                    onDecrementClickListener?.invoke(rc)
                }
            }
        }
    }

    override fun getItemCount(): Int =
        differ.currentList.size


    // set listeners
    fun setOnIncrementClickListener(onItemClickListener: ((RoomCart) -> Unit)?) {
        this.onIncrementClickListener = onItemClickListener
    }

    fun setOnDecrementClickListener(onItemClickListener: ((RoomCart) -> Unit)?) {
        this.onDecrementClickListener = onItemClickListener
    }

    // using DiffUtil to update our recycle
    // when update or change list of items
    private val differCallback = object : DiffUtil.ItemCallback<RoomCart>() {
        override fun areItemsTheSame(oldItem: RoomCart, newItem: RoomCart): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: RoomCart, newItem: RoomCart): Boolean =
            oldItem == newItem
    }

    val differ = AsyncListDiffer(this, differCallback)


    // private vars
    private var onIncrementClickListener: ((RoomCart) -> Unit)? = null
    private var onDecrementClickListener: ((RoomCart) -> Unit)? = null

    data class ViewHolder(val binding: ItemCartProductBinding) : RecyclerView.ViewHolder(binding.root)


}