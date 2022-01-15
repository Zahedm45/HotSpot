package com.example.hotspot.viewModel

import android.content.ContentValues.TAG
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.hotspot.model.User


class UsersAndIds() {

    companion object {


        private val userList = mutableListOf<User>()
        private var users  = MutableLiveData<List<User>>()
        private var ids = ArrayList<String>()

        init {
            users.value = userList
        }


        fun addUser(user: User): Boolean {
            if (user.uid != null && !ids.contains(user.uid)) {
                ids.add(user.uid!!)
                userList.add(user)
                users.value = userList
                return true
            }

            return false
        }



        fun removeUser(userIds: ArrayList<String>) {
            val usersToRemove = mutableListOf<User>()
            userIds.forEach {
                getUser(it)?.let { user ->
                    usersToRemove.add(user)
                }
            }
            ids.removeAll(userIds.toSet())
            userList.removeAll(usersToRemove)
            users.value = userList
        }




        fun getIds(): MutableList<String> {
            return ids
        }

        fun getUser() = users as LiveData<List<User>>


        fun getUser(userId: String): User? {

            if (ids.contains(userId)) {

                userList.forEach {
                    if (it.uid == userId) {
                        return it
                    }
                }
            }
            return null
        }

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
