package com.example.hotspot.viewModel

import com.example.hotspot.model.HotSpot
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class BeforeCheckInVM {


    companion object{

        fun setCheckedIn(hotSpot: HotSpot, userId: String, onSuccess: (() -> Unit)? ) {

            if (hotSpot.checkedIn!!.contains(userId)) {
                return
            }

            hotSpot.checkedIn?.add(userId)
            val db = Firebase.firestore
            db.collection("hotSpots").document(hotSpot.id!!).update("checkedIn", hotSpot.checkedIn)
                .addOnSuccessListener {
                    if (onSuccess != null) {
                        onSuccess()
                    }
                }
        }


    }



}