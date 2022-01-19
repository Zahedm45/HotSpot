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


        fun fetchCurrentUserFromDB() {
            val userId = Firebase.auth.uid
            if (userId != null) {

                SubRepository.getAndListenCurrentUserDB(userId) { user -> addUser(user) }
            }
        }


        private fun addUser(user: User) {
            if (user.isUserCheckedIn != "null" || user.isUserCheckedIn != null ) {
                SubRepository.getAndListenCurrentUserHotspot(user.isUserCheckedIn!!) {hotSpot -> addCurrentUserHotspot(hotSpot)}
            }
            currentUser.value = user
        }


        fun getCurrentUser() = currentUser as LiveData<User>


        fun addCurrentUserHotspot(hotSpot: HotSpot) {
            currentUserHotSpot.value = hotSpot
        }

    }


}