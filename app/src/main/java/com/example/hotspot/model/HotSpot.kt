package com.example.hotspot.model

data class HotSpot(
    val hotSpotName: String,
    val address: String,
    val overallRating: String,
    val checkedIn: ArrayList<String>
)
