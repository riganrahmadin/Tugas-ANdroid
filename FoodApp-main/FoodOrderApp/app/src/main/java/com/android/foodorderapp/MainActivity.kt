package com.android.foodorderapp

import androidx.appcompat.app.AppCompatActivity
import com.android.foodorderapp.adapters.RestaurantListAdapter.RestaurantListClickListener
import android.os.Bundle
import com.android.foodorderapp.R
import com.android.foodorderapp.model.RestaurantModel
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.foodorderapp.adapters.RestaurantListAdapter
import com.google.gson.Gson
import android.content.Intent
import com.android.foodorderapp.RestaurantMenuActivity
import android.widget.TextView
import android.widget.EditText
import androidx.appcompat.widget.SwitchCompat
import com.android.foodorderapp.adapters.PlaceYourOrderAdapter
import android.widget.CompoundButton
import android.text.TextUtils
import com.android.foodorderapp.OrderSucceessActivity
import android.app.Activity
import com.android.foodorderapp.adapters.MenuListAdapter.MenuListClickListener
import com.android.foodorderapp.adapters.MenuListAdapter
import android.widget.Toast
import com.android.foodorderapp.PlaceYourOrderActivity
import androidx.recyclerview.widget.GridLayoutManager
import com.android.foodorderapp.MainActivity
import java.io.*
import java.lang.Exception
import java.util.*

class MainActivity : AppCompatActivity(), RestaurantListClickListener {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val actionBar = supportActionBar
        actionBar!!.title = "Restaurant List"
        val restaurantModelList = restaurantData
        initRecyclerView(restaurantModelList)
    }

    private fun initRecyclerView(restaurantModelList: List<RestaurantModel>) {
        val recyclerView = findViewById<RecyclerView>(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)
        val adapter = RestaurantListAdapter(restaurantModelList, this)
        recyclerView.adapter = adapter
    }

    private val restaurantData: List<RestaurantModel>
        private get() {
            val `is` = resources.openRawResource(R.raw.restaurent)
            val writer: Writer = StringWriter()
            val buffer = CharArray(1024)
            try {
                val reader: Reader =
                    BufferedReader(InputStreamReader(`is`, "UTF-8"))
                var n: Int
                while (reader.read(buffer).also { n = it } != -1) {
                    writer.write(buffer, 0, n)
                }
            } catch (e: Exception) {
            }
            val jsonStr = writer.toString()
            val gson = Gson()
            val restaurantModels =
                gson.fromJson(
                    jsonStr,
                    Array<RestaurantModel>::class.java
                )
            return Arrays.asList(*restaurantModels)
        }

    override fun onItemClick(restaurantModel: RestaurantModel?) {
        val intent = Intent(this@MainActivity, RestaurantMenuActivity::class.java)
        intent.putExtra("RestaurantModel", restaurantModel)
        startActivity(intent)
    }
}