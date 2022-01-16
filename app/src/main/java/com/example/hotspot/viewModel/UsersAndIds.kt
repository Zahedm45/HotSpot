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



        fun removeUser(userId: String) {

            if (!checkedInMap.containsKey(userId)) {
                Log.i(TAG, "User does not contain in the list")
                return
            }


            var userToRemove = User()
            for (curr in userList) {
                if (curr.uid == userId) {
                    userToRemove = curr
                    break
                }
            }

            checkedInMap.remove(userId)
            userList.remove(userToRemove)
            users.value = userList

        }

       // fun getCheckedInMap() = checkedInMap as LiveData<*>



/*        fun getCheckedIn(): MutableList<CheckedInDB> {
            return checked
        }*/

        fun getUser() = users as LiveData<List<User>>




    }



}







/*            map.forEach {
                if (it.key == checkedIn.id) {
                    if (it.value != checkedIn.isInterested) {

                        checkedIn.isInterested?.let { isInterested ->
                                map.replace(it.key, isInterested)
                        }

                     //   users.value = userList
                    }
                }
            }*/



















/*
        fun getUser(userId: String): User? {

            if (checked.contains(userId)) {

                userList.forEach {
                    if (it.uid == userId) {
                        return it
                    }
                }
            }
            return null
        }
*/















/*       fun removeUser(user: User): Boolean {
            if (user.uid == null) {
                return false
                Log.i(ContentValues.TAG, "User id is null")
            }

            Log.i(TAG, "123456before remove $userList and user id is ${user.uid}")


            if (ids.contains(user.uid)){
                ids.remove(user.uid)
                userList.remove(user)
                users.value = userList
                Log.i(TAG, "123456after remove $userList and user id is ${user.uid}")
                return true

            }

            return false

        }
*/





/*    fun removeUser(userId: String) {
        if (ids.remove(userId)){

            val newTempList = userList
            for (user in newTempList) {
                if (user.uid == userId) {
                    userList.remove(user)
                    users.value = userList
                    break
                }
            }
        }
    }*/
