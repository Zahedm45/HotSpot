package com.example.hotspot.model

import android.content.ContentValues.TAG
import android.content.Context
import android.location.Geocoder
import android.os.Parcel
import android.os.Parcelable
import android.util.Log
import com.google.firebase.firestore.GeoPoint
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import java.util.*
import java.util.concurrent.ThreadLocalRandom
import kotlin.collections.ArrayList

data class HotSpot(
    var id: String? = null,
    var name: String? = null,
    var address: Address? = null,
    var geoPoint: GeoPoint? = null,
    var rating: Double? = null,
    var checkedIn: ArrayList<String>? = null
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readString(),
        parcel.readValue(Address::class.java.classLoader) as? Address,
        parcel.readValue(GeoPoint::class.java.classLoader) as? GeoPoint,
        parcel.readValue(Double::class.java.classLoader) as? Double,
        parcel.readValue(String::class.java.classLoader) as? ArrayList<String>
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(name)
        parcel.writeValue(rating)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<HotSpot> {
        override fun createFromParcel(parcel: Parcel): HotSpot {
            return HotSpot(parcel)
        }

        override fun newArray(size: Int): Array<HotSpot?> {
            return arrayOfNulls(size)
        }
    }


}


class SubClassForHotspot() {

    companion object {

        fun defineRH(name: String, latitude: Double, longitude: Double, context: Context) {
            val geocoder = Geocoder(context, Locale.getDefault())
            val address = Address()

            geocoder.let {

                it.getFromLocation(latitude, longitude, 1)?.let { addr ->
                    address.streetName = addr[0].thoroughfare
                    address.doorNumber = addr[0].subThoroughfare
                    address.floor = addr[0].featureName
                    address.town = addr[0].subLocality
                    address.postalCode = addr[0].postalCode
                    address.country = addr[0].countryName
                }
            }

            if (address.floor == address.doorNumber) {
                address.floor = "ground floor"
            }

            val randomNum = ThreadLocalRandom.current().nextDouble(3.0, 5.0).toFloat()
            val randomTwoDig = Math.round(randomNum * 10.0) / 10.0


            val hotSpot = HotSpot().apply {
                this.name = name
                this.address = address
                geoPoint = GeoPoint(latitude, longitude)
                rating = randomTwoDig
            }



            val db = Firebase.firestore
            db.collection("hotSpots").add(hotSpot)
                .addOnSuccessListener {
                    Log.i(TAG, "Successfully random Hotspots created")
                }

        }

    }


}












