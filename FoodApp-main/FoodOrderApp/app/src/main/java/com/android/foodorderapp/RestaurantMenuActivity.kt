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
import android.view.MenuItem
import android.view.View
import com.android.foodorderapp.adapters.MenuListAdapter.MenuListClickListener
import com.android.foodorderapp.adapters.MenuListAdapter
import android.widget.Toast
import com.android.foodorderapp.PlaceYourOrderActivity
import androidx.recyclerview.widget.GridLayoutManager
import com.android.foodorderapp.MainActivity
import com.android.foodorderapp.model.Menu
import java.util.ArrayList

class RestaurantMenuActivity : AppCompatActivity(), MenuListClickListener {
    private var menuList: List<Menu>? = null
    private var menuListAdapter: MenuListAdapter? = null
    private var itemsInCartList: MutableList<Menu?>? = null
    private var totalItemInCart = 0
    private var buttonCheckout: TextView? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_restaurant_menu)
        val restaurantModel: RestaurantModel = intent.getParcelableExtra("RestaurantModel")
        val actionBar = supportActionBar
        actionBar!!.setTitle(restaurantModel.name)
        actionBar.setSubtitle(restaurantModel.address)
        actionBar.setDisplayHomeAsUpEnabled(true)
        menuList = restaurantModel.menus
        initRecyclerView()
        buttonCheckout = findViewById(R.id.buttonCheckout)
        buttonCheckout.setOnClickListener(View.OnClickListener {
            if (itemsInCartList != null && itemsInCartList!!.size <= 0) {
                Toast.makeText(
                    this@RestaurantMenuActivity,
                    "Please add some items in cart.",
                    Toast.LENGTH_SHORT
                ).show()
                return@OnClickListener
            }
            restaurantModel.menus = itemsInCartList
            val i = Intent(this@RestaurantMenuActivity, PlaceYourOrderActivity::class.java)
            i.putExtra("RestaurantModel", restaurantModel)
            startActivityForResult(i, 1000)
        })
    }

    private fun initRecyclerView() {
        val recyclerView = findViewById<RecyclerView>(R.id.recyclerView)
        recyclerView.layoutManager = GridLayoutManager(this, 2)
        menuListAdapter = MenuListAdapter(menuList!!, this)
        recyclerView.adapter = menuListAdapter
    }

    override fun onAddToCartClick(menu: Menu?) {
        if (itemsInCartList == null) {
            itemsInCartList = ArrayList()
        }
        itemsInCartList!!.add(menu)
        totalItemInCart = 0
        for (m in itemsInCartList!!) {
            totalItemInCart = totalItemInCart + m!!.totalInCart
        }
        buttonCheckout!!.text = "Checkout ($totalItemInCart) items"
    }

    override fun onUpdateCartClick(menu: Menu?) {
        if (itemsInCartList!!.contains(menu)) {
            val index = itemsInCartList!!.indexOf(menu)
            itemsInCartList!!.removeAt(index)
            itemsInCartList!!.add(index, menu)
            totalItemInCart = 0
            for (m in itemsInCartList!!) {
                totalItemInCart = totalItemInCart + m!!.totalInCart
            }
            buttonCheckout!!.text = "Checkout ($totalItemInCart) items"
        }
    }

    override fun onRemoveFromCartClick(menu: Menu?) {
        if (itemsInCartList!!.contains(menu)) {
            itemsInCartList!!.remove(menu)
            totalItemInCart = 0
            for (m in itemsInCartList!!) {
                totalItemInCart = totalItemInCart + m!!.totalInCart
            }
            buttonCheckout!!.text = "Checkout ($totalItemInCart) items"
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> finish()
            else -> {}
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 1000 && resultCode == RESULT_OK) {
            //
            finish()
        }
    }
}