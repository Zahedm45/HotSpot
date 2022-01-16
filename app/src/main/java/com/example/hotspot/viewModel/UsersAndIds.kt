package com.example.hotspot.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.hotspot.model.CheckedInDB
import com.example.hotspot.model.User


class UsersAndIds() {

    companion object {


        private val userList = mutableListOf<User>()
        private var users  = MutableLiveData<List<User>>()
        private var checked = ArrayList<CheckedInDB>()

        init {
            users.value = userList
        }


        fun addUser(user: User, checkedInDB: CheckedInDB): Boolean {
/*            if (checked.contains(checkedInDB)) {
                return false
            }
            */

           // var checkedInDbToRemove = CheckedInDB()
            for (crr in checked) {

                if (crr.id == checkedInDB.id ) {
                    if (crr.isInterested != checkedInDB.isInterested) {
                       // checkedInDbToRemove = crr
                        removeUser(crr)
                        checked.add(checkedInDB)
                        userList.add(user)
                        users.value = userList

                    }
                    return false
                }
            }

/*            checked.add(checkedInDB)
            userList.add(user)
            users.value = userList
            return true*/
        }



        fun removeUser(checkedInDB: CheckedInDB) {

            if (!checked.contains(checkedInDB)) {
                return
            }

            var userToRemove = User()
            for (curr in userList) {
                if (curr.uid == checkedInDB.id) {
                    userToRemove = curr
                    break
                }

            }


            userList.remove(userToRemove)
            users.value = userList


            var checkedInDBToRemove = CheckedInDB()

            for (curr in checked) {
                if (curr.id == )

            }


            checked.remove(checkedInDB)


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
