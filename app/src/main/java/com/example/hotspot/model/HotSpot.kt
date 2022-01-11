package com.example.hotspot.model

import android.os.Parcel
import android.os.Parcelable
import com.google.firebase.firestore.GeoPoint

data class HotSpot(
    val hotSpotName: String? = null,
    val address: Address? = null,
    val geoPoint: GeoPoint? = null,
    val overallRating: Double? = null,
    val checkedIn: ArrayList<String>? = null
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString(),
        TODO("address"),
        TODO("geoPoint"),
        parcel.readValue(Double::class.java.classLoader) as? Double,
        TODO("checkedIn")
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(hotSpotName)
        parcel.writeValue(overallRating)
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

