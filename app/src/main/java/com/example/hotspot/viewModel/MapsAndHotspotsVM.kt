package com.example.hotspot.viewModel

import com.example.hotspot.model.HotSpot
import com.example.hotspot.repository.Repository
import com.google.android.gms.maps.GoogleMap
import com.google.firebase.firestore.ListenerRegistration

class MapsAndHotspotsVM {

    companion object {

        var showHotSpotReg: ListenerRegistration? = null

         var hotSpots: ArrayList<HotSpot>? = null
        private var googleMap: GoogleMap? = null


        fun showHotSpots(onSuccess: (hotSpots: ArrayList<HotSpot>) -> Unit) {

            hotSpots?.let {
                onSuccess(hotSpots!!)
            }

           showHotSpotReg = Repository.getAndListenHotSpotsDB({ hotSpots -> onSuccess(hotSpots)}, null)

        }



    }



}