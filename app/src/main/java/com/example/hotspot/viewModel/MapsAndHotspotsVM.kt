package com.example.hotspot.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.hotspot.model.HotSpot
import com.example.hotspot.repository.Repository
import com.google.android.gms.maps.GoogleMap
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.ListenerRegistration

class MapsAndHotspotsVM : ViewModel(){



    companion object {

        var snackbar: Snackbar? = null
        var showHotSpotReg: ListenerRegistration? = null
        var hotSpots: ArrayList<HotSpot>? = null

        fun showHotSpots(onSuccess: (hotSpots: ArrayList<HotSpot>) -> Unit) {

            hotSpots?.let {
                onSuccess(hotSpots!!)
            }

            showHotSpotReg =
                Repository.getAndListenHotSpotsDB({ hotSpots -> onSuccess(hotSpots) }, null)

        }


    }






}