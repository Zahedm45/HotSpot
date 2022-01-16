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
      //  private var checked = ArrayList<CheckedInDB>()


        var checkedInMap = mutableMapOf<String, Boolean>()
        private var isInterestedChanged = MutableLiveData<Boolean>()



        init {
            users.value = userList
            isInterestedChanged.value = false
        }




        fun addUser(user: User, checkedInDB: CheckedInDB) {


            user.uid?.let {

                if (checkedInMap.containsKey(it)) {
                    updateUser(checkedInDB)
                    return
                }


                checkedInDB.isInterested?.let {
                    checkedInMap[user.uid.toString()] = it
                    userList.add(user)
                    users.value = userList
                }
            }
        }




        fun updateUser(checkedIn: CheckedInDB) {

            if (checkedIn.id == null || checkedIn.isInterested == null) {
                return
            }

            if (checkedInMap.get(checkedIn.id) != checkedIn.isInterested ) {
                checkedInMap.replace(checkedIn.id!!, checkedIn.isInterested!!)
                setIsInterestedChanged()

            }

        }

        private fun setIsInterestedChanged() {
            if (isInterestedChanged.value == true) {
                isInterestedChanged.value = false

            } else {
                val i = true
                isInterestedChanged.value = i
            }
        }



        fun removeUser(userIds: ArrayList<String>) {

           // val anyChange = false

            userIds.forEach {
                userList.removeIf {user -> user.uid == it}.apply {
                    checkedInMap.remove(it)
                    users.value = userList
                    Log.i(TAG, "Removed... UsersAndIds")
                }

            }
            Log.i(TAG, "Removed... dd")

        }

        fun getUser() = users as LiveData<List<User>>




    }


}



