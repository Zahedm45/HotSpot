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

        var function: Unit? = null


        fun setListenerToCheckedInListDB(hotSpot: HotSpot, function: () -> Unit) {
            this.function = function()

            if (hotSpot.id != null) {
                Repository.getAndListenCheckedInIds(hotSpot.id!!
                ) { checkedIn -> onSuccessSnapShotIds(checkedIn) }
            }

        }


        private fun onSuccessSnapShotIds(checkedInIds: ArrayList<String>) {
            val ids = checkedInUsersAndIds.getIds()

            if (ids != checkedInIds) {

                checkedInIds.forEach {
                    if (!ids.contains(it)) {
                        Repository.getCheckedInUserFromDB(it) {
                                user -> onnSuccessSnapshotUser(user) }


                    }
                }

            }

        }



        private fun onnSuccessSnapshotUser(user: User) {
            checkedInUsersAndIds.addUser(user)
            function
        }

    }


}










class UserAndIds() {
    var users  = MutableLiveData<List<User>>()
    private var ids = ArrayList<String>()

    init {

        users.value = listOf()
    }


    fun addUser(user: User): Boolean {

        if (user.uid == null) {
            return false
            Log.i(TAG, "User id is null")
        }

        if (!ids.contains(user.uid)) {

            user.uid?.let {
                ids.add(it)
                val i = users
                i.value?.plus(user)
                users = i

            }

/*
            users.value?.plus(user).apply {
                users.postValue(this)
            }*/

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
        users.value?.minus(user).apply {
            users.postValue(this)
        }
    }



    fun getIds(): MutableList<String> {
        return ids
    }

    fun getUser(): MutableLiveData<List<User>> {
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

