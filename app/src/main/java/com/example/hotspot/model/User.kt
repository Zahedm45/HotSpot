package com.example.hotspot.model

import android.graphics.Bitmap
import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class User(
    var name: String? = null,
    var age: Int? = null,
    var emailAddress: String? = null,
    var bio: String? = null,
    val gender: String? = null,
    var bitmapImg: Bitmap? = null
) : Parcelable
