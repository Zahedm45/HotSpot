package com.example.hotspot.viewModel

import android.content.ContentValues.TAG
import android.util.Log
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class BeforeCheckInVM {






    companion object{

        fun setCheckedIn(hotSpotId: String, userId: String, onSuccess: (() -> Unit)? ) {

            val userIdArr: ArrayList<String> = ArrayList()
            userIdArr.add(userId)
            val db = Firebase.firestore
            val colRef = db.collection("hotSpots").document(hotSpotId)

/*            colRef.update({

            })
            colRef.add.addOnSuccessListener {


                Log.i(TAG, "ID is ${it.documents}")
            }*/
/*            colRef.add(userIdArr)
                .addOnSuccessListener {
                    if (onSuccess != null) {
                        onSuccess()
                    }
                }*/

        }
    }



}