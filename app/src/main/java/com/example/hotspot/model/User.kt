package com.example.hotspot.model

import android.graphics.Bitmap

data class User(
    var name: String? = null,
    var age: Int? = null,
    var emailAddress: String? = null,
    var bio: String? = null,
    var gender: String? = null,
    var bitmapImg: Bitmap? = null
)
