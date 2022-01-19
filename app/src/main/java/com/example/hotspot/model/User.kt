package com.example.hotspot.model

import android.graphics.Bitmap

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

   // var gender: String? = null,
  //  var bitmapImg: Bitmap? = null
) {




}