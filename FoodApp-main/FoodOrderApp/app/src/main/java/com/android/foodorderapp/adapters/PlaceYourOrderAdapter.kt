package com.android.foodorderapp.adapters

import androidx.recyclerview.widget.RecyclerView
import android.view.ViewGroup
import android.view.LayoutInflater
import android.view.View
import android.widget.ImageView
import com.android.foodorderapp.R
import com.bumptech.glide.Glide
import android.widget.TextView
import com.android.foodorderapp.model.RestaurantModel
import com.android.foodorderapp.adapters.RestaurantListAdapter.RestaurantListClickListener
import com.android.foodorderapp.model.Menu

class PlaceYourOrderAdapter(private var menuList: List<Menu>) :
    RecyclerView.Adapter<PlaceYourOrderAdapter.MyViewHolder>() {
    fun updateData(menuList: List<Menu>) {
        this.menuList = menuList
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.place_order_recycler_row, parent, false)
        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.menuName.text = menuList[position].name
        holder.menuPrice.text = "Price: $" + String.format(
            "%.2f",
            menuList[position].price * menuList[position].totalInCart
        )
        holder.menuQty.text = "Qty: " + menuList[position].totalInCart
        Glide.with(holder.thumbImage)
            .load(menuList[position].url)
            .into(holder.thumbImage)
    }

    override fun getItemCount(): Int {
        return menuList.size
    }

    class MyViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var menuName: TextView
        var menuPrice: TextView
        var menuQty: TextView
        var thumbImage: ImageView

        init {
            menuName = view.findViewById(R.id.menuName)
            menuPrice = view.findViewById(R.id.menuPrice)
            menuQty = view.findViewById(R.id.menuQty)
            thumbImage = view.findViewById(R.id.thumbImage)
        }
    }
}