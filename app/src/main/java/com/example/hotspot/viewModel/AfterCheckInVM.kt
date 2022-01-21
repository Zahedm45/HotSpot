package com.example.hotspot.viewModel

import com.example.hotspot.model.CheckedInDB
import com.example.hotspot.model.HotSpot
import com.example.hotspot.model.User
import com.example.hotspot.repository.Repository
import com.example.hotspot.repository.SubRepository
import com.google.firebase.firestore.ListenerRegistration
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.Default
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.launch


class AfterCheckInVM {

    companion object {
        var checkedInListenerRig: ListenerRegistration? = null
        var amountOfUsersToFetch = 0
        var isNavigatedFromCheckInBtn = false




        fun setListenerToCheckedInListDB(hotSpot: HotSpot) {
            if (hotSpot.id != null) {

                checkedInListenerRig = Repository.getAndListenCheckedInIds(hotSpot.id!!
                ) { checkedIn -> onSuccessSnapShotIds(checkedIn) }
            }

        }


        private fun onSuccessSnapShotIds(newCheckedIn: ArrayList<CheckedInDB>) {
            val oldCheckedIn = UsersAndIds.checkedInMap

            CoroutineScope(Default).launch {
                val newIdList = ArrayList<String>()
                newCheckedIn.forEach {
                    it.id?.let { id -> newIdList.add(id) }
                }
                val tempOldC = oldCheckedIn
                val toRemove = ArrayList<String>()

                for (curr in tempOldC) {
                    if (!newIdList.contains(curr.key)) {
                        toRemove.add(curr.key)
                    }
                }

                CoroutineScope(Main).launch {
                    if (!toRemove.isNullOrEmpty()){
                        UsersAndIds.removeUser(toRemove)
                    }
                }

            }



            for (curr in newCheckedIn) {

                curr.id?.let {
                    if (!oldCheckedIn.containsKey(it)) {
                        amountOfUsersToFetch++
                        Repository.getCheckedInUserFromDB(it, curr) { user, crr -> onnSuccessGetUser(user, crr) }

                    } else {
                        UsersAndIds.updateUser(curr)
                    }
                }
            }

        }


        private fun onnSuccessGetUser(user: User, checkedInDB: CheckedInDB) {
            UsersAndIds.addUser(user, checkedInDB)
        }


        fun setIsInterested(isInterested: Boolean, hotSpotId: String) {
            val userId = DataHolder.getCurrentUser().value?.uid
            if (userId != null) {
                Repository.updateIsInterestedDB(hotSpotId, userId, isInterested)
            }
        }


        fun getHotSpotImgFromDB(
            hotSpotId: String,
            onSuccess: (img: String) -> Unit){


            SubRepository.getHotSpotImgFromDB(hotSpotId) { img -> onSuccess(img) }

        }













    }


}

