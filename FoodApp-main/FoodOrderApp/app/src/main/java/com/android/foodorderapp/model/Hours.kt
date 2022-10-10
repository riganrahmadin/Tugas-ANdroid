package com.android.foodorderapp.model

import android.os.Parcel
import android.os.Parcelable
import android.os.Parcelable.Creator
import com.android.foodorderapp.model.Hours
import com.android.foodorderapp.model.RestaurantModel
import java.text.SimpleDateFormat
import java.util.*

class Hours {
    var sunday: String? = null
    var monday: String? = null
    var tuesday: String? = null
    var wednesday: String? = null
    var thursday: String? = null
    var friday: String? = null
    var saturday: String? = null
    val todaysHours: String?
        get() {
            val calendar = Calendar.getInstance()
            val date = calendar.time
            val day = SimpleDateFormat("EEEE", Locale.ENGLISH).format(date.time)
            return when (day) {
                "Sunday" -> sunday
                "Monday" -> monday
                "Tuesday" -> tuesday
                "Wednesday" -> wednesday
                "Thursday" -> thursday
                "Friday" -> friday
                "Saturday" -> saturday
                else -> sunday
            }
        }
}