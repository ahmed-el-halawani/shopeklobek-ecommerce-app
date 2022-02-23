package com.stash.shopeklobek.ui.home.cart

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.DiffUtil.ItemCallback
import androidx.recyclerview.widget.RecyclerView
import com.newcore.wezy.shareprefrances.SettingsPreferences
import com.stash.shopeklobek.R
import com.stash.shopeklobek.databinding.ItemCartProductBinding

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

        holder.binding.apply{

            ivProductImage.setImageResource(R.drawable.test2)

            tvPrice.text = "300"

            tvCurrencyUnit.text = "$"

            tvCounter.text = "1"

            tvProductTitle.text = "Product Title test test test test"
            tvQuality.text = "Good quality"

            btnRemove.setOnClickListener{
                val count = Integer.parseInt(tvCounter.text.toString()) - 1
                if(count<=0)
                    return@setOnClickListener
                try {
                    tvCounter.text = (count).toString()
                }catch (t:Throwable){
                    tvCounter.text = "1"
                }
            }

            btnAdd.setOnClickListener{
                try {
                    val count = Integer.parseInt(tvCounter.text.toString()) + 1
                    tvCounter.text = (count).toString()
                }catch (t:Throwable){
                    tvCounter.text = "1"
                }
            }



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
        override fun areItemsTheSame(oldItem: String, newItem: String): Boolean =  oldItem == newItem


        override fun areContentsTheSame(oldItem: String, newItem: String): Boolean =
            oldItem == newItem
    }

    val differ = AsyncListDiffer(this, differCallback)


    // private vars
//    private var onItemClickListener: ((WeatherLang) -> Unit)? = null

    data class ViewHolder(val binding: ItemCartProductBinding) : RecyclerView.ViewHolder(binding.root)




}