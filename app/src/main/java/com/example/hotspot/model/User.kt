package com.example.hotspot.model

import android.graphics.Bitmap
import android.os.Parcel
import android.os.Parcelable

data class User(
    var uid: String? = null,
    var name: String? = null,
    var age: Int? = null,
    var emailAddress: String? = null,
    var bio: String? = null,
    val gender: String? = null,
    var bitmapImg: Bitmap? = null,
    var day: Int? = null,
    var month: Int? = null,
    var year: Int? = null,
    var isUserCheckedIn: String? = null,
    var favoriteHotspots: MutableCollection<HotSpot>? = null,
) : Parcelable {



    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readString(),
        parcel.readValue(Int::class.java.classLoader) as? Int,
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readParcelable(Bitmap::class.java.classLoader),
        parcel.readValue(Int::class.java.classLoader) as? Int,
        parcel.readValue(Int::class.java.classLoader) as? Int,
        parcel.readValue(Int::class.java.classLoader) as? Int,
        parcel.readString(),
        parcel.readValue(HotSpot::class.java.classLoader) as? MutableCollection<HotSpot>
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(uid)
        parcel.writeString(name)
        parcel.writeValue(age)
        parcel.writeString(emailAddress)
        parcel.writeString(bio)
        parcel.writeString(gender)
        parcel.writeParcelable(bitmapImg, flags)
        parcel.writeValue(day)
        parcel.writeValue(month)
        parcel.writeValue(year)
        parcel.writeString(isUserCheckedIn)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<User> {
        override fun createFromParcel(parcel: Parcel): User {
            return User(parcel)
        }

        override fun newArray(size: Int): Array<User?> {
            return arrayOfNulls(size)
        }
    }


}
