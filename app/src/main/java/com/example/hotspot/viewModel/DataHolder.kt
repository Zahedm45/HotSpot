package com.example.hotspot.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.hotspot.model.HotSpot
import com.example.hotspot.model.User
import com.example.hotspot.repository.SubRepository
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase


class DataHolder {

    companion object {

        private var currentUser = MutableLiveData<User>()
        private var currentUserHotSpot = MutableLiveData<HotSpot>()
       // var hotSpotId: String? = null




        fun fetchCurrentUserFromDB() {
            val userId = Firebase.auth.uid
            if (userId != null) {

                SubRepository.getAndListenCurrentUserDB(userId) { user -> addUser(user) }
            }
        }


        private fun addUser(user: User) {
            currentUser.value = user
            getAndListenCurrentUserHotspot()
        }


        fun getCurrentUser() = currentUser as LiveData<User>


        private fun addCurrentUserHotspot(hotSpot: HotSpot) {

            if (!hotSpot.id.isNullOrEmpty() && !hotSpot.name.isNullOrEmpty() ) {
                currentUserHotSpot.value = hotSpot

            }
        }


        fun getCurrentUserHotspot() = currentUserHotSpot as LiveData<HotSpot>




        fun getAndListenCurrentUserHotspot() {
            val user = currentUser.value
            if (user?.isUserCheckedIn != "null" || user.isUserCheckedIn != null ) {
                SubRepository.getAndListenCurrentUserHotspotDB(user?.isUserCheckedIn!!) { hotSpot -> addCurrentUserHotspot(hotSpot)}

            }


        }
    }


}