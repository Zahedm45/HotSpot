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


        private var isInterestedTrueList = MutableLiveData<List<String>>()
        private var isInterestedTrueListHelper = mutableListOf<String>()


        var checkedInMap = mutableMapOf<String, Boolean>()
       // private var isInterestedChanged = MutableLiveData<Boolean>()



        init {
            users.value = userList
         //   isInterestedChanged.value = false
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

                }
            }
        }




        fun updateUser(checkedIn: CheckedInDB) {

            if (checkedIn.id == null || checkedIn.isInterested == null) {
                return
            }

            if (checkedInMap.get(checkedIn.id) != checkedIn.isInterested ) {
                checkedInMap.replace(checkedIn.id!!, checkedIn.isInterested!!)

                if (checkedIn.isInterested!!) {
                    isInterestedTrueListHelper.add(checkedIn.id!!)
                    isInterestedTrueList.value = isInterestedTrueListHelper
                }

              //  setIsInterestedChanged()

            }

        }

/*        private fun setIsInterestedChanged() {
            if (isInterestedChanged.value == true) {
                isInterestedChanged.value = false

            } else {
                val i = true
                isInterestedChanged.value = i
            }
        }*/



        fun removeUser(userIds: ArrayList<String>) {

           // val anyChange = false

            userIds.forEach {
                userList.removeIf {user -> user.uid == it}.apply {
                    checkedInMap.remove(it)
                    users.value = userList
                    Log.i(TAG, "Removed... UsersAndIds")
                }

            }

        }

        fun getUser() = users as LiveData<List<User>>




    }


}



