package com.example.hotspot.viewModel

import com.example.hotspot.model.CheckedInDB
import com.example.hotspot.model.HotSpot
import com.example.hotspot.model.User
import com.example.hotspot.repository.Repository
import com.google.firebase.firestore.ListenerRegistration
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class BeforeCheckInVM {


    companion object{
        var getAndListenCheckedInIdsRegis: ListenerRegistration? = null


        fun setCheckedInDB(hotSpot: HotSpot, user: User, onSuccess: (() -> Unit)? ) {
            val userId = user.uid

            hotSpot.checkedIn?.forEach {
                if (it.id == userId) {
                    return
                }
            }


            hotSpot.checkedIn?.add(CheckedInDB(id = userId))
            val db = Firebase.firestore
            db.collection("hotSpots2").document(hotSpot.id!!).update("checkedIn", hotSpot.checkedIn)
                .addOnSuccessListener {
                    if (onSuccess != null) {
                        onSuccess()
                    }
                }
        }



        fun getAndListenCheckedInIdsDB(hotSpotId: String, onSuccess: ((hotSpots: ArrayList<String>) -> Unit)) {
            getAndListenCheckedInIdsRegis = Repository
                .getAndListenCheckedInIds(hotSpotId) { hotSpot -> onSuccess(hotSpot) }
        }
    }



}