package com.example.hotspot.viewModel

import com.example.hotspot.model.CheckedInDB
import com.example.hotspot.model.HotSpot
import com.example.hotspot.model.User
import com.example.hotspot.repository.Repository
import com.google.firebase.firestore.ListenerRegistration



class AfterCheckInVM {

    companion object {
        var checkedInListenerRig: ListenerRegistration? = null
        fun setListenerToCheckedInListDB(hotSpot: HotSpot) {
            if (hotSpot.id != null) {

                checkedInListenerRig = Repository.getAndListenCheckedInIds(hotSpot.id!!
                ) { checkedIn -> onSuccessSnapShotIds(checkedIn) }
            }

        }


        private fun onSuccessSnapShotIds(newCheckedIn: ArrayList<CheckedInDB>) {
            val oldCheckedIn = UsersAndIds.checkedInMap
            for (curr in newCheckedIn) {

                curr.id?.let {
                    if (!oldCheckedIn.containsKey(it)) {
                        Repository.getCheckedInUserFromDB(it, curr) { user, crr -> onnSuccessGetUser(user, crr) }

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
                    toRemove.add(curr.key)
                }
            }

            if (!toRemove.isNullOrEmpty()){
                UsersAndIds.removeUser(toRemove)
            }
        }


        private fun onnSuccessGetUser(user: User, checkedInDB: CheckedInDB) {
            UsersAndIds.addUser(user, checkedInDB)
        }


        fun setIsInterested(isInterested: Boolean, hotSpotId: String) {
            val userId = DataHolder.currentUser?.uid
            if (userId != null) {
                Repository.updateIsInterestedDB(hotSpotId, userId, isInterested)
            }
        }

    }


}

