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
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.Default
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class BeforeCheckInVM {


    companion object{
        var getAndListenCheckedInIdsRegis: ListenerRegistration? = null
        private var addedCheckedInAndHotspotId = UsersAndIds.checkedInMap

        private lateinit var checkedInDB : CheckedInDB
        private lateinit var user: User

        fun setCheckedInDB(hotSpot: HotSpot, user: User, onSuccess: (() -> Unit)? ) {


            val db = Firebase.firestore

            hotSpot.id?.let { hotSpotId ->
                user.uid?.let { userId ->
                    checkedInDB = CheckedInDB(userId, true)
                    this.user = user
                   // Log.i(TAG, "User was $addedCheckedInAndHotspotId")
/*                    if (addedCheckedInAndHotspotId.containsKey(userId)) {
                        Log.i(TAG, "User was previously added to the database (BeforeCheckInVM) 1")
                        return
                    }*/
                    ///setCheckedInLocal(user, checkedInDB)
                    db.collection("hotSpots3").document(hotSpotId).collection("checkedIn").document(userId)
                        .set(checkedInDB)
                        .addOnSuccessListener {
                            if (onSuccess != null) {
                                onSuccess()
                            }
                        }

                    setCheckedInLocal()
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



        private fun setCheckedInLocal () {

            CoroutineScope(Default).launch {
                delay(300)
                CoroutineScope(Main).launch {

                    if (!addedCheckedInAndHotspotId.containsKey(user.uid)) {
                        UsersAndIds.addUser(user, checkedInDB)

                    } else {
                        Log.i(TAG, "User was previously added to the database (BeforeCheckInVM) 1")
                    }

                }
            }
        }
    }



}