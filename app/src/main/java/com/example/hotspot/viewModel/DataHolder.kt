package com.example.hotspot.viewModel

import androidx.lifecycle.MutableLiveData
import com.example.hotspot.model.CheckedInDB
import com.example.hotspot.model.User
import com.example.hotspot.repository.Repository
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase


class DataHolder {

    companion object {
        var currentUser: User? = null

        var currentUser2 = MutableLiveData<User>()


        fun fetchCurrentUserFromDB() {
            val userId = Firebase.auth.uid
            if (userId != null) {

                val checkedInDB = CheckedInDB(id = userId)

                Repository.getCheckedInUserFromDB(userId, checkedInDB) {
                        user, checkedIn -> addToCheckedInUsersList(user, checkedIn) }
            }
        }


        private fun addToCheckedInUsersList(user: User, checkedInDB: CheckedInDB) {
            currentUser = user
        }


    }


}