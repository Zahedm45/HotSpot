package com.example.hotspot.model

import android.graphics.Bitmap

data class User(
    var uid: String? = null,
    var name: String? = null,
    var age: Int? = null,
    var emailAddress: String? = null,
    var bio: String? = null,
    val gender: String,
    var bitmapImg: Bitmap? = null,
    var day: Int,
    var month: Int,
    var year: Int,
    var gender: String? = null,
    var bitmapImg: Bitmap? = null
)
