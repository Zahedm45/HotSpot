package com.example.hotspot.model

import android.graphics.Bitmap

data class EditProfile(

    var bitmapImg: Bitmap? = null,
    var newDisplayName: String? = null,
    var newMail: String? = null,
    var newBio: String? = null
)
