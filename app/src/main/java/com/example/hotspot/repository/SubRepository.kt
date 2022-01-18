package com.example.hotspot.repository

import android.util.Log
import com.example.hotspot.model.HotSpot
import com.example.hotspot.other.network.TAG
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import java.lang.ref.Reference

class SubRepository {

    companion object {

        fun addHotSpotDB(hotSpotId: String, userId: String) {
            Log.i(TAG, "HotSpot added...1")

            val db = Firebase.firestore

            val map = mutableMapOf<String, String>()
            map.put("hotspotId", hotSpotId)

            db.collection("users").document(userId).collection("favoriteHotspots").document(hotSpotId)
                .set(map)
                .addOnSuccessListener {
                    Log.i(TAG, "HotSpot added...")
                }
        }

    }


}