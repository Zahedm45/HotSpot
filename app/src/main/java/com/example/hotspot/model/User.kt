package com.example.hotspot.model

import android.graphics.Bitmap

data class User(
    var name: String,
    var age: Int,
    var emailAddress: String,
    var bio: String? = null,
    val gender: String,
    var bitmapImg: Bitmap? = null
)