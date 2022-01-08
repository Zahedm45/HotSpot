package com.example.hotspot.viewModel

import com.example.hotspot.model.HotSpot
import com.example.hotspot.repository.Repository
import com.google.firebase.firestore.QuerySnapshot

class MapsAndHotspotsVM {

    companion object {

        private val hotSpots: ArrayList<HotSpot>? = null



        fun showHotSpots() {
            Repository.getHotSpots(null, null)

        }









//
//        private fun onSuccess(documents: QuerySnapshot): ArrayList<HotSpot> {
//
//            documents.forEach {
//                it.get("na")
//            }
//
//        }

    }








}