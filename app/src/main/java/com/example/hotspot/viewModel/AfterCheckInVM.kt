package com.example.hotspot.viewModel

import android.content.ContentValues.TAG
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.hotspot.model.HotSpot
import com.example.hotspot.model.User
import com.example.hotspot.repository.Repository
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ListenerRegistration
import com.google.firebase.ktx.Firebase


/*typealias users = MutableList<User>
typealias ids = MutableList<String>

typealias usersAndIds = MutableList<String>*/

class AfterCheckInVM {

    companion object {
        var checkedInListenerRig: ListenerRegistration? = null
        private var isInterested : MutableLiveData<Boolean> = MutableLiveData(true)

        fun setListenerToCheckedInListDB(hotSpot: HotSpot) {
            if (hotSpot.id != null) {
                checkedInListenerRig = Repository.getAndListenCheckedInIds(hotSpot.id!!
                ) { checkedIn -> onSuccessSnapShotIds(checkedIn) }
            }

        }


        private fun onSuccessSnapShotIds(checkedInIds: ArrayList<String>) {

            val ids = UsersAndIds.getIds()
            if (ids == checkedInIds) {
                Log.i(TAG, "Same ids ${ids} and checkedInd $checkedInIds")
                return
            }

            for (id in checkedInIds) {
                if (!ids.contains(id)) {
                    Repository.getCheckedInUserFromDB(id) { user -> onnSuccessSnapshotUser(user) }
                }
            }


            val toRemove = ArrayList<String>()
            for (id in ids) {
                if (!checkedInIds.contains(id)) {

                    toRemove.add(id)
                }
            }

            UsersAndIds.removeUser(toRemove)

        }



        private fun onnSuccessSnapshotUser(user: User) {
            UsersAndIds.addUser(user)
        }




        fun setIsInterestedDB(isInterested: Boolean, hotSpot: HotSpot) {

            val userId = Firebase.auth.uid
            hotSpot.checkedIn?.let {
                for (checkedIn in it) {
                    if (checkedIn.id == userId){
                        checkedIn.isInterested = isInterested
                        break
                    }
                }
            }

            hotSpot.id?.let { hotSpot.checkedIn?.let { it1 ->
                Repository.updateIsInterestedDB(it,
                    it1
                )
            } }

        }





        fun setIsInterested(isInterested: Boolean) {

           this.isInterested.postValue(isInterested)
        }


        fun getIsInterested() = isInterested as LiveData<Boolean>
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

