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

class PlaceYourOrderActivity : AppCompatActivity() {
    private var inputName: EditText? = null
    private var inputAddress: EditText? = null
    private var inputCity: EditText? = null
    private var inputState: EditText? = null
    private var inputZip: EditText? = null
    private var inputCardNumber: EditText? = null
    private var inputCardExpiry: EditText? = null
    private var inputCardPin: EditText? = null
    private var cartItemsRecyclerView: RecyclerView? = null
    private var tvSubtotalAmount: TextView? = null
    private var tvDeliveryChargeAmount: TextView? = null
    private var tvDeliveryCharge: TextView? = null
    private var tvTotalAmount: TextView? = null
    private var buttonPlaceYourOrder: TextView? = null
    private var switchDelivery: SwitchCompat? = null
    private var isDeliveryOn = false
    private var placeYourOrderAdapter: PlaceYourOrderAdapter? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_place_your_order)
        val restaurantModel: RestaurantModel = intent.getParcelableExtra("RestaurantModel")
        val actionBar = supportActionBar
        actionBar!!.setTitle(restaurantModel.name)
        actionBar.setSubtitle(restaurantModel.address)
        actionBar.setDisplayHomeAsUpEnabled(true)
        inputName = findViewById(R.id.inputName)
        inputAddress = findViewById(R.id.inputAddress)
        inputCity = findViewById(R.id.inputCity)
        inputState = findViewById(R.id.inputState)
        inputZip = findViewById(R.id.inputZip)
        inputCardNumber = findViewById(R.id.inputCardNumber)
        inputCardExpiry = findViewById(R.id.inputCardExpiry)
        inputCardPin = findViewById(R.id.inputCardPin)
        tvSubtotalAmount = findViewById(R.id.tvSubtotalAmount)
        tvDeliveryChargeAmount = findViewById(R.id.tvDeliveryChargeAmount)
        tvDeliveryCharge = findViewById(R.id.tvDeliveryCharge)
        tvTotalAmount = findViewById(R.id.tvTotalAmount)
        buttonPlaceYourOrder = findViewById(R.id.buttonPlaceYourOrder)
        switchDelivery = findViewById(R.id.switchDelivery)
        cartItemsRecyclerView = findViewById(R.id.cartItemsRecyclerView)
        buttonPlaceYourOrder.setOnClickListener(View.OnClickListener {
            onPlaceOrderButtonClick(
                restaurantModel
            )
        })
        switchDelivery.setOnCheckedChangeListener(CompoundButton.OnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked) {
                inputAddress.setVisibility(View.VISIBLE)
                inputCity.setVisibility(View.VISIBLE)
                inputState.setVisibility(View.VISIBLE)
                inputZip.setVisibility(View.VISIBLE)
                tvDeliveryChargeAmount.setVisibility(View.VISIBLE)
                tvDeliveryCharge.setVisibility(View.VISIBLE)
                isDeliveryOn = true
                calculateTotalAmount(restaurantModel)
            } else {
                inputAddress.setVisibility(View.GONE)
                inputCity.setVisibility(View.GONE)
                inputState.setVisibility(View.GONE)
                inputZip.setVisibility(View.GONE)
                tvDeliveryChargeAmount.setVisibility(View.GONE)
                tvDeliveryCharge.setVisibility(View.GONE)
                isDeliveryOn = false
                calculateTotalAmount(restaurantModel)
            }
        })
        initRecyclerView(restaurantModel)
        calculateTotalAmount(restaurantModel)
    }

    private fun calculateTotalAmount(restaurantModel: RestaurantModel) {
        var subTotalAmount = 0f
        for (m in restaurantModel.menus) {
            subTotalAmount += m.price * m.totalInCart
        }
        tvSubtotalAmount!!.text = "$" + String.format("%.2f", subTotalAmount)
        if (isDeliveryOn) {
            tvDeliveryChargeAmount!!.text =
                "$" + String.format("%.2f", restaurantModel.delivery_charge)
            subTotalAmount += restaurantModel.delivery_charge
        }
        tvTotalAmount!!.text = "$" + String.format("%.2f", subTotalAmount)
    }

    private fun onPlaceOrderButtonClick(restaurantModel: RestaurantModel) {
        if (TextUtils.isEmpty(inputName!!.text.toString())) {
            inputName!!.error = "Please enter name "
            return
        } else if (isDeliveryOn && TextUtils.isEmpty(inputAddress!!.text.toString())) {
            inputAddress!!.error = "Please enter address "
            return
        } else if (isDeliveryOn && TextUtils.isEmpty(inputCity!!.text.toString())) {
            inputCity!!.error = "Please enter city "
            return
        } else if (isDeliveryOn && TextUtils.isEmpty(inputState!!.text.toString())) {
            inputState!!.error = "Please enter zip "
            return
        } else if (TextUtils.isEmpty(inputCardNumber!!.text.toString())) {
            inputCardNumber!!.error = "Please enter card number "
            return
        } else if (TextUtils.isEmpty(inputCardExpiry!!.text.toString())) {
            inputCardExpiry!!.error = "Please enter card expiry "
            return
        } else if (TextUtils.isEmpty(inputCardPin!!.text.toString())) {
            inputCardPin!!.error = "Please enter card pin/cvv "
            return
        }
        //start success activity..
        val i = Intent(this@PlaceYourOrderActivity, OrderSucceessActivity::class.java)
        i.putExtra("RestaurantModel", restaurantModel)
        startActivityForResult(i, 1000)
    }

    private fun initRecyclerView(restaurantModel: RestaurantModel) {
        cartItemsRecyclerView!!.layoutManager = LinearLayoutManager(this)
        placeYourOrderAdapter = PlaceYourOrderAdapter(restaurantModel.menus)
        cartItemsRecyclerView!!.adapter = placeYourOrderAdapter
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == 1000) {
            setResult(RESULT_OK)
            finish()
        }
        super.onActivityResult(requestCode, resultCode, data)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> finish()
            else -> {}
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onBackPressed() {
        super.onBackPressed()
        setResult(RESULT_CANCELED)
        finish()
    }
}