package com.example.hotspot.viewModel

import com.example.hotspot.model.User
import com.example.hotspot.repository.Repository

class AfterCheckInVM {

    companion object {
        var checkedInUsers = ArrayList<User>()


        fun getCheckedInUserFromDB(usersId: ArrayList<String>) {
            usersId.forEach {
                Repository.getCheckedInUserFromDB(it, {user -> addToCheckedInUsersList(user)})
            }
        }



        private fun addToCheckedInUsersList(user: User) {
            checkedInUsers.add(user)
        }

    }


}