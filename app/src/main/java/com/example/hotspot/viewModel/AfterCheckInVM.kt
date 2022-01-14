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
        val checkedInUsersAndIds = UserAndIds()


        fun setListenerToCheckedInListDB(hotSpot: HotSpot) {
            if (hotSpot.id != null) {
                Repository.getAndListenCheckedInIds(hotSpot.id!!
                ) { checkedIn -> onSuccessSnapShotIds(checkedIn) }
            }

        }


        private fun onSuccessSnapShotIds(checkedIn: ArrayList<String>) {
            val ids = checkedInUsersAndIds.getIds()


            if (ids != checkedIn) {

                checkedIn.forEach {
                    if (!ids.contains(it)) {
                        Repository.getCheckedInUserFromDB(it) {
                                user -> onnSuccessSnapshotUser(user) }
                    }
                }

            }

        }



        private fun onnSuccessSnapshotUser(user: User) {
            checkedInUsersAndIds.addUser(user)
        }

    }


}










class UserAndIds() {
    private var users  = MutableLiveData<MutableList<User>>()
    private var ids = ArrayList<String>()



    fun addUser(user: User): Boolean {

        if (user.uid == null) {
            return false
            Log.i(TAG, "User id is null")
        }

        if (!ids.contains(user.uid)) {

            user.uid?.let {
                ids.add(it)
            }
            users.value?.add(user)
            return true



/*            if (ids.add(user.uid!!) && users.value!!.add(user)){
                return true

            } else {

                // TODO: need to take care of if the user was not added
            }*/
        }

        return false
    }



     fun removeUser(user: User) {
        if (user.uid == null) {
            return
            Log.i(TAG, "User id is null")
        }
        ids.remove(user.uid)
        users.value!!.remove(user)
    }



    fun getIds(): MutableList<String> {
        return ids
    }

    fun getUser(): MutableLiveData<MutableList<User>> {
        return users
    }










}





// var checkedInIds = ArrayList<String>()

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

