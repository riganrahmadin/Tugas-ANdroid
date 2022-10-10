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
import android.os.Handler
import com.android.foodorderapp.adapters.MenuListAdapter.MenuListClickListener
import com.android.foodorderapp.adapters.MenuListAdapter
import android.widget.Toast
import com.android.foodorderapp.PlaceYourOrderActivity
import androidx.recyclerview.widget.GridLayoutManager
import com.android.foodorderapp.MainActivity

class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        val actionBar = supportActionBar
        actionBar!!.hide()
        Handler().postDelayed({
            startActivity(Intent(this@SplashActivity, MainActivity::class.java))
            finish()
        }, 2000)
    }
}