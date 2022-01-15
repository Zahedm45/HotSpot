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
        val checkedInUsersAndIds = UsersAndIds()

       // var function: Unit? = null


        fun setListenerToCheckedInListDB(hotSpot: HotSpot, function: () -> Unit) {
          //  this.function = function()

            if (hotSpot.id != null) {
                Repository.getAndListenCheckedInIds(hotSpot.id!!
                ) { checkedIn -> onSuccessSnapShotIds(checkedIn) }
            }

        }


        private fun onSuccessSnapShotIds(checkedInIds: ArrayList<String>) {
            var ids = checkedInUsersAndIds.getIds()

            if (ids == checkedInIds) {
                return
            }


            checkedInIds.forEach {
                if (!ids.contains(it)) {
                    Repository.getCheckedInUserFromDB(it) { user -> onnSuccessSnapshotUser(user) }
                }
            }


            val subIds = ids

            for (id in subIds) {
                if (!checkedInIds.contains(id)) {
                    val tempUser = checkedInUsersAndIds.getUser(id)
                    if (tempUser != null) {
                        checkedInUsersAndIds.removeUser(tempUser)
                        subIds.clear()
                        break
                    }

                }

            }


        }



        private fun onnSuccessSnapshotUser(user: User) {
            checkedInUsersAndIds.addUser(user)
        }

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

