package com.example.hotspot.viewModel

import android.content.ContentValues.TAG
import android.util.Log
import com.example.hotspot.model.HotSpot
import com.example.hotspot.repository.Repository
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions

class MapsAndHotspotsVM {

    companion object {

        private val hotSpots: ArrayList<HotSpot>? = null
        private var googleMap: GoogleMap? = null



        fun showHotSpots(onSuccess: (hotSpots: ArrayList<HotSpot>) -> Unit) {

            Repository.getHotSpots({ hotSpots -> onSuccess(hotSpots)}, null)





        }







    }








}