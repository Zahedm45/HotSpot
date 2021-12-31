package com.example.hotspot.model

import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.net.Uri
import com.google.firebase.storage.FileDownloadTask

data class UserProfile(
    var name: String,
    var age: Int,
    var emailAddress: String,
    var userName: String,
    var password: String,
    var bio: String? = null,
    val gender: String,
    var bitmapImg: Bitmap? = null
    )
