package com.stash.shopeklobek.ui.profile

import android.content.Context
import android.content.SharedPreferences
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.stash.shopeklobek.R
import com.stash.shopeklobek.model.entities.room.RoomOrder
import com.stash.shopeklobek.ui.MainActivity
import com.stash.shopeklobek.ui.home.favorites.FavoritesFragmentDirections
import com.stash.shopeklobek.ui.profile.orders_details.OrdersDetailsFragmentArgs
import com.stash.shopeklobek.utils.Constants
import com.stash.shopeklobek.utils.toCurrency

class AdapterOrder(var orders: List<RoomOrder>) :
    RecyclerView.Adapter<AdapterOrder.ViewHolder>() {


    inner class ViewHolder(val view: View) : RecyclerView.ViewHolder(view) {

        val tvDate: TextView
            get() = view.findViewById(R.id.tv_date_order)
        val tvPrice: TextView
            get() = view.findViewById(R.id.tv_price_order)
        val tvState: TextView
            get() = view.findViewById(R.id.tv_state_order)
        val order: CardView
            get() = view.findViewById(R.id.order)


    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val viwe: View =
            LayoutInflater.from(parent.context).inflate(R.layout.item_order, parent, false)
        return ViewHolder(viwe)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        holder.tvDate.text = orders[position].order.getDate()
        holder.tvPrice.text = orders[position].order.finalPrice?.toCurrency(holder.itemView.context)
        holder.tvState.text = orders[position].order.financialStatus


        holder.order.context?.let {
            val sharedPreferences: SharedPreferences = it.getSharedPreferences(
                "sharedPrefFile",
                Context.MODE_PRIVATE
            )
            holder.order.setOnClickListener {
                if (sharedPreferences.getInt(Constants.ONCLICK_PROFILE, -1).equals(2)) {
                    val action =
                        OrdersFragmentDirections.actionOrdersFragmentToNavOrderDetails(orders[position])
                    it.findNavController().navigate(action)
                }
            }
        }

    }

    @JvmName("setOrders1")
    fun setOrders(orders: List<RoomOrder>) {
        this.orders = orders
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int = orders.size
}