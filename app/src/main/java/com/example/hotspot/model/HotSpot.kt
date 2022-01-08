package com.example.hotspot.model

import com.google.android.gms.maps.model.LatLng
import com.google.firebase.firestore.GeoPoint

data class HotSpot(
    val hotSpotName: String? = null,
    val address: GeoPoint? = null,
    val overallRating: Double? = null,
    val checkedIn: ArrayList<String>? = null
)
