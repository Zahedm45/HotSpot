package com.example.hotspot.viewModel

import android.util.Log
import android.widget.ImageView
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.hotspot.model.CheckedInDB
import com.example.hotspot.model.HotSpot
import com.example.hotspot.model.User
import com.example.hotspot.other.network.TAG
import com.example.hotspot.repository.Repository
import com.example.hotspot.repository.SubRepository
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

        var hotSpotsImg = mutableMapOf<String, String>()
        private val repository = Repository






        fun setCheckedInDB(hotSpot: HotSpot, user: User, onSuccess: (() -> Unit)? ) {


            val db = Firebase.firestore

            hotSpot.id?.let { hotSpotId ->
                user.uid?.let { userId ->
                    updateIsUserCheckIn(hotSpotId)
                    val i = addedCheckedInAndHotspotId.get(userId)
/*                    if (i == false || i == true) {
                        checkedInDB = CheckedInDB(userId, i)
                    } else {
                        checkedInDB = CheckedInDB(userId, true)
                    }*/

                    checkedInDB = CheckedInDB(userId, true)

                    this.user = user
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




        fun addHotSpotDB(hotSpotId: String, userId: String) {
            SubRepository.addHotSpotDB(hotSpotId, userId)
        }

        fun deleteHotSpotDB(hotSpotId: String, userId: String){
            SubRepository.deleteHotSpotDB(hotSpotId,userId)
        }

        private fun updateIsUserCheckIn(hotSpotId: String){

            //User(isUserCheckedIn = true)
            repository.updateIsUserCheckedIn(hotSpotId)
        }



    }



}