package com.example.hotspot.viewModel

import com.example.hotspot.model.CheckedInDB
import com.example.hotspot.model.HotSpot
import com.example.hotspot.repository.Repository
import com.google.firebase.firestore.ListenerRegistration
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class BeforeCheckInVM {


    companion object{
        var getAndListenCheckedInIdsRegis: ListenerRegistration? = null


        fun setCheckedIn(hotSpot: HotSpot, userId: String, onSuccess: (() -> Unit)? ) {

/*
            hotSpot.checkedIn?.forEach {
                if (it.id == userId) {
                    return
                }
            }


            val userToAdd = CheckedInDB(id = userId)

            hotSpot.checkedIn?.add(userToAdd)
            val db = Firebase.firestore
            db.collection("hotSpots").document(hotSpot.id!!).update("checkedIn", hotSpot.checkedIn)
                .addOnSuccessListener {
                    if (onSuccess != null) {
                        onSuccess()
                    }
                }*/
        }



        fun getAndListenCheckedInIdsDB(hotSpotId: String, onSuccess: ((hotSpots: ArrayList<CheckedInDB>) -> Unit)) {
            getAndListenCheckedInIdsRegis = Repository
                .getAndListenCheckedInIds(hotSpotId) { hotSpot -> onSuccess(hotSpot) }
        }
    }



}