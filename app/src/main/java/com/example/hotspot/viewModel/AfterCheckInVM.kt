package com.example.hotspot.viewModel

import android.content.ContentValues.TAG
import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.example.hotspot.model.HotSpot
import com.example.hotspot.model.User
import com.example.hotspot.repository.Repository


/*typealias users = MutableList<User>
typealias ids = MutableList<String>

typealias usersAndIds = MutableList<String>*/

class AfterCheckInVM {

    companion object {
       // var checkedInUsers = ArrayList<User>()
       // var subOnSuccess: ((User) -> Unit?)? = null

/*        val users = ArrayList<User>()
        val ids = ArrayList<String>()*/
        val checkedInUsers = MutableLiveData<UserAndIds>()
        var checkedInIds = ArrayList<String>()

     /*
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
        }*/







        fun setListenerToCheckedInListDB(hotSpot: HotSpot) {
            if (hotSpot.id != null) {
                Repository.getAndListenCheckedInIds(hotSpot.id!!,
                    {checkedIn -> onSuccessSnapShot(checkedIn) })
            }

        }


        fun onSuccessSnapShot(checkedIn: ArrayList<String>) {

            if (checkedInIds != checkedIn) {
                checkedInIds = checkedIn

            }

        }


    }


}




class UserAndIds() {
   private var users: MutableList<User> = ArrayList()
   private var ids: MutableList<String> = ArrayList()



    fun addUser(user: User): Boolean {

        if (user.uid == null) {
            return false
            Log.i(TAG, "User id is null")
        }

        if (!ids.contains(user.uid)) {
            if (ids.add(user.uid!!) && users.add(user)){
                return true

            } else {

                // TODO: need to take care of if the user was not added
            }
        }

        return false
    }



    fun removeUser(user: User) {
        if (user.uid == null) {
            return
            Log.i(TAG, "User id is null")
        }
        ids.remove(user.uid)
        users.remove(user)
    }










}