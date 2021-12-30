package com.example.hotspot.model

import android.graphics.Bitmap
import android.net.Uri

data class User(
    var name: String,
    var age: Int,
    var emailAddress: String,
    var userName: String,
    var password: String,
    var bio: String? = null,
    val gender: String,
    var img: Uri? = null,
    var bitmapImg: Bitmap? = null
)