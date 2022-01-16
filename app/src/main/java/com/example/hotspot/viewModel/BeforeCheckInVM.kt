package com.example.hotspot.viewModel

import android.util.Log
import com.example.hotspot.model.CheckedInDB
import com.example.hotspot.model.HotSpot
import com.example.hotspot.model.User
import com.example.hotspot.other.network.TAG
import com.example.hotspot.repository.Repository
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ListenerRegistration
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class BeforeCheckInVM {


    companion object{
        var getAndListenCheckedInIdsRegis: ListenerRegistration? = null


        // TODO it someone delete it from the database
        private var addedCheckedInAndHotspotId = UsersAndIds.checkedInMap

        fun setCheckedInDB(hotSpot: HotSpot, user: User, onSuccess: (() -> Unit)? ) {


            val db = Firebase.firestore

            hotSpot.id?.let { hotSpotId ->
                user.uid?.let { userId ->

                    if (addedCheckedInAndHotspotId.containsKey(userId)) {

                        Log.i(TAG, "User was previously added to the database (BeforeCheckInVM)")
                        return
                    }

                    val checkedInDB = CheckedInDB(userId, true)
                    setCheckedInLocal(user, checkedInDB)
                    db.collection("hotSpots3").document(hotSpotId).collection("checkedIn").document(userId)
                        .set(checkedInDB)
                        .addOnSuccessListener {
                            if (onSuccess != null) {
                                onSuccess()
                            }
                        }

                } ?: run {
                    Log.i(TAG, "User id is null (BeforeCheckedInVM)")
                }

            } ?: run{
                Log.i(TAG, "HotSpot id is null (BeforeCheckedInVM)")
            }

        }



        fun getAndListenCheckedInIdsDB(hotSpotId: String, onSuccess: ((checkedIn: ArrayList<CheckedInDB>) -> Unit)) {

            getAndListenCheckedInIdsRegis = Repository
                .getAndListenCheckedInIds(hotSpotId) { checkedIn -> onSuccess(checkedIn) }
        }



        private fun setCheckedInLocal (user: User, checkedInDB: CheckedInDB) {
            UsersAndIds.addUser(user, checkedInDB)
        }
    }



}