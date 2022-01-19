package com.example.hotspot.viewModel

import androidx.navigation.NavDirections
import com.example.hotspot.model.HotSpot
import com.example.hotspot.repository.Repository
import com.google.android.gms.maps.GoogleMap
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.firestore.ListenerRegistration

class MapsAndHotspotsVM {

    companion object {

        var snackbar: Snackbar? = null
        var showHotSpotReg: ListenerRegistration? = null

         var hotSpots: ArrayList<HotSpot>? = null
         var isAppJustOpened = true


        fun showHotSpots(onSuccess: (hotSpots: ArrayList<HotSpot>) -> Unit) {

            hotSpots?.let {
                onSuccess(hotSpots!!)
            }

            showHotSpotReg =
                Repository.getAndListenHotSpotsDB({ hotSpots -> onSuccess(hotSpots) }, null)

        }



    }



}