package com.example.hotspot.viewModel

import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class BeforeCheckInVM {






    companion object{

        fun setCheckedIn(hotSpotId: String, userId: String, onSuccess: (() -> Unit)? ) {

            val db = Firebase.firestore
            val colRef = db.collection("hotSpots").document(hotSpotId).collection("checkedIn")
            colRef.add(userId)
                .addOnSuccessListener {
                    if (onSuccess != null) {
                        onSuccess()
                    }
                }

        }
    }



}