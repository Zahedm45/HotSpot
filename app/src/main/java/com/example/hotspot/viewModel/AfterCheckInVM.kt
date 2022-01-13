package com.example.hotspot.viewModel

import com.example.hotspot.model.User
import com.example.hotspot.repository.Repository

class AfterCheckInVM {

    companion object {
        var checkedInUsers = ArrayList<User>()
        var subOnSuccess: ((User) -> Unit?)? = null



        fun getCheckedInUserFromDB(usersId: ArrayList<String>) {
            usersId.forEach {
                Repository.getCheckedInUserFromDB(it, {user -> addToCheckedInUsersList(user)})
            }
        }

        private fun addToCheckedInUsersList(user: User) {
            checkedInUsers.add(user)
        }



        fun getCheckedInUserFromDB(
            usersId: ArrayList<String>,
            onSuccess: (user: User) -> Unit
            ) {

            checkedInUsers.forEach {
                onSuccess(it)
            }

            subOnSuccess = onSuccess

            usersId.forEach {
                Repository.getCheckedInUserFromDB(it, {user -> subOnSuccess(user)})
            }

        }


        private fun subOnSuccess(user: User) {
            subOnSuccess?.let { it(user) }
            checkedInUsers.add(user)
        }

    }


}