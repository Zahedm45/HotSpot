package com.example.hotspot.viewModel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.hotspot.model.CheckedInDB
import com.example.hotspot.model.HotSpot
import com.example.hotspot.model.User
import com.example.hotspot.other.network.TAG
import com.example.hotspot.repository.Repository
import com.google.firebase.firestore.ListenerRegistration


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


        private fun onSuccessSnapShotIds(newCheckedIn: ArrayList<CheckedInDB>) {

            val oldCheckedIn = UsersAndIds.checkedInMap

            Log.i(TAG, "AfterCheckedInMv")
            for (curr in newCheckedIn) {

                curr.id?.let {

                    if (!oldCheckedIn.containsKey(it)) {
                        Repository.getCheckedInUserFromDB(
                            it,
                            curr
                        ) { user, crr -> onnSuccessGetUser(user, crr) }

                    } else {
                        UsersAndIds.updateUser(curr)
                    }
                }
            }


            val newIdList = ArrayList<String>()
            newCheckedIn.forEach {
                it.id?.let { id -> newIdList.add(id) }
            }


            val toRemove = ArrayList<String>()
            for (curr in oldCheckedIn) {

                if (!newIdList.contains(curr.key)) {
                    UsersAndIds.removeUser(curr.key)

                    // toRemove.add(curr.key)
                }
            }

        }




        private fun onnSuccessGetUser(user: User, checkedInDB: CheckedInDB) {
            UsersAndIds.addUser(user, checkedInDB)
        }




        private fun updateIsInterestedDB(isInterested: Boolean, hotSpot: HotSpot) {

/*            val userId = Firebase.auth.uid
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
            } }*/

        }





        fun setIsInterested(isInterested: Boolean, hotSpot: HotSpot) {
            if (this.isInterested.value != isInterested) {
                this.isInterested.postValue(isInterested)
              //  updateIsInterestedDB(isInterested, hotSpot)

            }
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






















/*            val ids = UsersAndIds.getIds()
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

            UsersAndIds.removeUser(toRemove)*/