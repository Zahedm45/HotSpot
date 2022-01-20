package com.example.hotspot.viewModel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.hotspot.model.CheckedInDB
import com.example.hotspot.model.User
import com.example.hotspot.other.network.TAG


class UsersAndIds() {

    companion object {


        private val userList = mutableListOf<User>()
        private var users  = MutableLiveData<List<User>>()


        private var isInterestedTrueList = MutableLiveData<List<String>>()
        private val isInterestedTrueListHelper = mutableListOf<String>()


        var checkedInMap = mutableMapOf<String, Boolean>()



        init {
            users.value = userList
            isInterestedTrueList.value = isInterestedTrueListHelper
        }




        fun addUser(user: User, checkedInDB: CheckedInDB) {


            user.uid?.let { id ->

                if (checkedInMap.containsKey(id)) {
                    updateUser(checkedInDB)
                    return
                }


                checkedInDB.isInterested?.let { isInterested ->
                    checkedInMap[user.uid.toString()] = isInterested
                    userList.add(user)
                    users.value = userList

                    if (isInterested) {
                        isInterestedTrueListHelper.add(id)
                        isInterestedTrueList.value = isInterestedTrueListHelper
                    }
                    Log.i(TAG, "UsersAndIds.... user added")


                }
            }
        }




        fun updateUser(checkedIn: CheckedInDB) {

            if (checkedIn.id == null || checkedIn.isInterested == null) {
                return
            }

            if (checkedInMap[checkedIn.id] != checkedIn.isInterested ) {
                checkedInMap.replace(checkedIn.id!!, checkedIn.isInterested!!)

                if (checkedIn.isInterested!!) {
                    isInterestedTrueListHelper.add(checkedIn.id!!)

                } else {
                    isInterestedTrueListHelper.remove(checkedIn.id!!)
                }

                isInterestedTrueList.value = isInterestedTrueListHelper

                //  setIsInterestedChanged()
                Log.i(TAG, "UsersAndIds.... user updated")


            }

        }


        fun removeUser(userIds: ArrayList<String>) {

            userIds.forEach { id ->
                userList.removeIf {user -> user.uid == id}.apply {
                    checkedInMap.remove(id)
                    users.value = userList
                    isInterestedTrueListHelper.remove(id)

                    Log.i(TAG, "UsersAndIds.... user removed")
                }

            }

        }

        fun getUser() = users as LiveData<List<User>>


        fun getIsInterestedTrueList() = isInterestedTrueList as LiveData<List<String>>


    }


}

