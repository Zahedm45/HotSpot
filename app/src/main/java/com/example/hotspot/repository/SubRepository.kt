package com.example.hotspot.repository

import android.content.ContentValues
import android.graphics.BitmapFactory
import android.nfc.Tag
import android.util.Log
import com.example.hotspot.model.CheckedInDB
import com.example.hotspot.model.HotSpot
import com.example.hotspot.model.User
import com.example.hotspot.other.network.TAG
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.lang.ref.Reference

class SubRepository {

    companion object {

        val db = Firebase.firestore
        val map = mutableMapOf<String, String>()

        fun addHotSpotDB(hotSpotId: String, userId: String) {
            Log.i(TAG, "HotSpot added...1")

           // val db = Firebase.firestore

            Log.i(TAG, "hotSpot id $hotSpotId, and user $userId")
            //val map = mutableMapOf<String, String>()
            map.put("hotspotId", hotSpotId)

            db.collection("users").document(userId).collection("favoriteHotspots").document(hotSpotId)
                .set(map)
                .addOnSuccessListener {
                    Log.i(TAG, "HotSpot added...")
                }
        }

        fun deleteHotSpotDB(hotSpotId: String, userId : String){
            Log.i(TAG, "HotSpot deleted...")
            map.put("hotspotId", hotSpotId)

            db.collection("users").document(userId).collection("favoriteHotspots").document(hotSpotId)
                .delete()
                .addOnSuccessListener {
                    Log.i(TAG, "HotSpot deleted...")
                }


        }


        fun getAndListenCurrentUserDB(
            usersId: String,
            onSuccess: ((user: User) -> Unit)

        ) {

            val db = Firebase.firestore
            db.collection("users").document(usersId)
                .addSnapshotListener { value, error ->

                    if (value != null && value.exists()) {

                       // Log.i(TAG, "player .. ${value.get("isUserCheckedIn")}")
                        value.toObject<User>()?.apply {
                            this.uid = usersId
                            if (this.isUserCheckedIn == null) {
                               this.isUserCheckedIn = value.get("isUserCheckedIn").toString()
                            }

                            val ref =
                                FirebaseStorage.getInstance().getReference("/images/${usersId}")
                            val ONE_MEGABYTE: Long = (1824 * 1824).toLong()
                            ref.getBytes(ONE_MEGABYTE).addOnSuccessListener {
                                if (it != null) {
                                    this.bitmapImg = BitmapFactory.decodeByteArray(it, 0, it.size)
                                    Log.i(TAG, "Player found $this")
                                    onSuccess(this)

                                } else Log.i(TAG, "User's image can not be fetched ($this)")

                            }
                        }
                    }


                    if (error != null) {
                        Log.w(ContentValues.TAG, "Listen failed.", error)
                        return@addSnapshotListener
                    }


                }
        }



    }


}