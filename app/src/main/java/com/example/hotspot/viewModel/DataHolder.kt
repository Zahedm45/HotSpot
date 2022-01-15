package com.example.hotspot.viewModel

import com.example.hotspot.model.User
import com.example.hotspot.repository.Repository
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase


class DataHolder {

    companion object {
        var currentUser: User? = null


        fun getCurrentUserFromDB() {
            val userId = Firebase.auth.uid
            if (userId != null) {
                Repository.getCheckedInUserFromDB(userId) {
                        user -> addToCheckedInUsersList(user)
                }
            }
        }


        private fun addToCheckedInUsersList(user: User) {
            currentUser = user
        }
    }


}