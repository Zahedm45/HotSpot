package com.example.hotspot.model

import com.google.firebase.firestore.GeoPoint

data class CheckedInDB(
    var id: String? = null,
//    var geoPoint: GeoPoint? = null,
    var isInterested: Boolean? = true

    //var checkedInList: ArrayList<SubCheckedIn>? = null
)
