package com.example.hotspot.viewModel

import com.example.hotspot.model.CheckedInDB
import com.example.hotspot.model.HotSpot
import com.example.hotspot.model.SubCheckedIn
import com.example.hotspot.repository.Repository
import com.google.firebase.firestore.ListenerRegistration
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class BeforeCheckInVM {


    companion object{
        var getAndListenCheckedInIdsRegis: ListenerRegistration? = null


        fun setCheckedIn(hotSpot: HotSpot, userId: String, onSuccess: (() -> Unit)? ) {

            hotSpot.checkedIn?.checkedInList?.forEach {
                if (it.id == userId) {
                    return
                }
            }

/*            val subCheckedIn = ArrayList<SubCheckedIn>()
            subCheckedIn.add(SubCheckedIn(id = userId))

            val userToAdd = CheckedInDB(subCheckedIn)*/

            hotSpot.checkedIn?.checkedInList?.add(SubCheckedIn(id = userId))
            val db = Firebase.firestore
            db.collection("hotSpots").document(hotSpot.id!!).update("checkedIn", hotSpot.checkedIn)
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