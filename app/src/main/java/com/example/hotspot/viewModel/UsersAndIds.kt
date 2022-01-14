package com.example.hotspot.viewModel

import android.content.ContentValues
import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.example.hotspot.model.User


class UsersAndIds() {
    private val userList = mutableListOf<User>()
    var users  = MutableLiveData<List<User>>()
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



    fun removeUser(user: User) {
        if (user.uid == null) {
            return
            Log.i(ContentValues.TAG, "User id is null")
        }

        if (ids.remove(user.uid)){
            userList.remove(user)
            users.value = userList
        }

    }






    fun getIds(): MutableList<String> {
        return ids
    }

    fun getUser(): MutableLiveData<List<User>> {
        return users
    }


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




    fun removeUser(userId: String) {
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
    }


}
