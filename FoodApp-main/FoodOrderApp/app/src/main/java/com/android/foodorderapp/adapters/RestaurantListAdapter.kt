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

class RestaurantListAdapter(
    private var restaurantModelList: List<RestaurantModel>,
    private val clickListener: RestaurantListClickListener
) : RecyclerView.Adapter<RestaurantListAdapter.MyViewHolder>() {
    fun updateData(restaurantModelList: List<RestaurantModel>) {
        this.restaurantModelList = restaurantModelList
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.recycler_row, parent, false)
        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.restaurantName.text = restaurantModelList.get(position).name
        holder.restaurantAddress.text = "Address: " + restaurantModelList.get(position).address
        holder.restaurantHours.text =
            "Today's hours: " + restaurantModelList.get(position).hours.todaysHours
        holder.itemView.setOnClickListener(View.OnClickListener {
            clickListener.onItemClick(
                restaurantModelList[position]
            )
        })
        Glide.with(holder.thumbImage)
            .load(restaurantModelList[position].image)
            .into(holder.thumbImage)
    }

    override fun getItemCount(): Int {
        return restaurantModelList.size
    }

    class MyViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var restaurantName: TextView
        var restaurantAddress: TextView
        var restaurantHours: TextView
        var thumbImage: ImageView

        init {
            restaurantName = view.findViewById(R.id.restaurantName)
            restaurantAddress = view.findViewById(R.id.restaurantAddress)
            restaurantHours = view.findViewById(R.id.restaurantHours)
            thumbImage = view.findViewById(R.id.thumbImage)
        }
    }

    interface RestaurantListClickListener {
        fun onItemClick(restaurantModel: RestaurantModel?)
    }
}