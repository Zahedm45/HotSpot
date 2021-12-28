package com.example.hotspot.model

import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.net.Uri
import com.google.firebase.storage.FileDownloadTask

data class User(
    var name: String? = null,
    var age: Int? = null,
    var emailAddress: String? = null,
    var userName: String? = null,
    var password: String? = null,
    var bio: String? = null,
    val gender: String,
    var img: Uri? = null,
    var bitmapImg: Bitmap? = null

     // TO-DO ....
    )
