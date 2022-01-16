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
        private var checked = ArrayList<CheckedInDB>()

        private var map = mutableMapOf<String, Boolean>()

        init {
            users.value = userList
        }


        fun addUser(user: User, checkedInDB: CheckedInDB) {


            user.uid?.let {

                if (map.containsKey(it)) {
                    updateUser(checkedInDB)
                    return
                }


                checkedInDB.isInterested?.let {
                    map[user.uid.toString()] = it
                    userList.add(user)
                    users.value = userList
                }
            }

        }

        private fun updateUser(userId: CheckedInDB) {



        }


        fun removeUser(userId: String) {

            if (!map.containsKey(userId)) {
                return
                Log.i(TAG, "")
            }


            var userToRemove = User()
            for (curr in userList) {
                if (curr.uid == userId) {
                    userToRemove = curr
                    break
                }
            }

            map.remove(userId)
            userList.remove(userToRemove)
            users.value = userList

        }




        fun getCheckedIn(): MutableList<CheckedInDB> {
            return checked
        }

        fun getUser() = users as LiveData<List<User>>


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

    }



}

















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
