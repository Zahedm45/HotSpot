package com.example.hotspot.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.hotspot.model.CheckedInDB
import com.example.hotspot.model.User
import com.example.hotspot.repository.Repository
import com.example.hotspot.repository.SubRepository
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase


class DataHolder {

    companion object {
       // var currentUser: User? = null

        private var currentUser = MutableLiveData<User>()


        fun fetchCurrentUserFromDB() {
            val userId = Firebase.auth.uid
            if (userId != null) {


                SubRepository.getAndListenCurrentUserDB(userId) { user -> onSuccessUserListener(user) }


/*
                val checkedInDB = CheckedInDB(id = userId)

                Repository.getCheckedInUserFromDB(userId, checkedInDB) {
                        user, checkedIn -> addToCheckedInUsersList(user, checkedIn) }*/
            }
        }


        private fun addToCheckedInUsersList(user: User, checkedInDB: CheckedInDB) {
           // currentUser = user
        }


        private fun onSuccessUserListener(user: User) {

            currentUser.value = user

/*            if (currentUser.value != user) {
                currentUser.value = user
            }*/

        }


        fun getCurrentUser() = currentUser as LiveData<User>


    }


}