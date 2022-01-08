package com.example.hotspot.model

import com.google.android.gms.maps.model.LatLng
import com.google.firebase.firestore.GeoPoint

data class HotSpot(
    val hotSpotName: String,
    val address: GeoPoint,
    val overallRating: Double?,
    val checkedIn: ArrayList<String>?
)
