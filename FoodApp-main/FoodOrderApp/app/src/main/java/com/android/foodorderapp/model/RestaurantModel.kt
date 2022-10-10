package com.android.foodorderapp.model

import android.os.Parcel
import android.os.Parcelable
import android.os.Parcelable.Creator
import com.android.foodorderapp.model.Hours
import com.android.foodorderapp.model.RestaurantModel

class RestaurantModel protected constructor(`in`: Parcel) : Parcelable {
    var name: String?
    var address: String?
    var image: String?
    var delivery_charge: Float
    var hours: Hours? = null
    var menus: List<Menu>?
    override fun describeContents(): Int {
        return 0
    }

    override fun writeToParcel(dest: Parcel, flags: Int) {
        dest.writeString(name)
        dest.writeString(address)
        dest.writeString(image)
        dest.writeFloat(delivery_charge)
        dest.writeTypedList(menus)
    }

    companion object {
        val CREATOR: Creator<RestaurantModel> = object : Creator<RestaurantModel?> {
            override fun createFromParcel(`in`: Parcel): RestaurantModel? {
                return RestaurantModel(`in`)
            }

            override fun newArray(size: Int): Array<RestaurantModel?> {
                return arrayOfNulls(size)
            }
        }
    }

    init {
        name = `in`.readString()
        address = `in`.readString()
        image = `in`.readString()
        delivery_charge = `in`.readFloat()
        menus = `in`.createTypedArrayList(Menu.Companion.CREATOR)
    }
}