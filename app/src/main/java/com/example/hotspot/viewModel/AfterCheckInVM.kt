package com.example.hotspot.viewModel

import android.content.ContentValues.TAG
import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.example.hotspot.model.HotSpot
import com.example.hotspot.model.User
import com.example.hotspot.repository.Repository

class AfterCheckInVM {

    companion object {
       // var checkedInUsers = ArrayList<User>()
       // var subOnSuccess: ((User) -> Unit?)? = null

        val checkedInUsers = MutableLiveData<ArrayList<User>>()
        var checkedInIds = ArrayList<String>()

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







        fun setListenerToCheckedInListDB(hotSpot: HotSpot) {
            if (hotSpot.id != null) {
                Repository.getAndListenCheckedInList(hotSpot.id!!, {checkedIn -> d(checkedIn) })
            }

        }


        fun d(checkedIn: ArrayList<String>) {

            if (checkedInIds != checkedIn) {
                checkedInIds = checkedIn
            }

        }

    }


}