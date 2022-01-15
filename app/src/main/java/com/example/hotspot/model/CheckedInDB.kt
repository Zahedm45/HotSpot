package com.example.hotspot.model

import com.google.firebase.firestore.GeoPoint

data class CheckedInDB(
    var checkedInList: ArrayList<SubCheckedIn>? = null
)

data class SubCheckedIn(
    var id: String? = null,
    var geoPoint: GeoPoint? = null,
    var isInterested: Boolean? = null)