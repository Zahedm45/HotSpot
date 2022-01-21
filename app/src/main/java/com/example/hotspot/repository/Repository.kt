package com.example.hotspot.repository

import android.app.Activity
import android.content.ContentValues
import android.content.ContentValues.TAG
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.icu.number.NumberFormatter.with
import android.icu.number.NumberRangeFormatter.with
import android.location.Location
import android.util.Log
import com.example.hotspot.model.CheckedInDB
import com.example.hotspot.model.HotSpot
import com.example.hotspot.model.User
import com.example.hotspot.viewModel.PersonalProfileVM
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.ListenerRegistration
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.squareup.picasso.Picasso
import kotlinx.coroutines.*
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.tasks.await
import java.io.ByteArrayOutputStream
import java.lang.Exception

class Repository {


    companion object {

        fun deleteUser(){
            try {
                val user = Firebase.auth.currentUser!!
                val db = Firebase.firestore
                db.collection("users").document(user.uid)
                    .delete().addOnCompleteListener() {
                        if (it.isSuccessful) {
                            user.delete()
                                .addOnCompleteListener { task ->
                                    if (task.isSuccessful) {
                                    }
                                }
                        }
                    }
            } catch (e : Exception){
                e.printStackTrace()
            }
        }

        fun getFirebaseUser() : FirebaseUser?{
            return Firebase.auth.currentUser
        }

        fun addProfileToFirebase(
            user: User,
            onSuccess: (() -> Unit),
            onFailure: ((msg: String) -> Unit)

        ) {
            val fbUser = Firebase.auth.currentUser

            if (fbUser == null) {
                // something went wrong
                Log.w(ContentValues.TAG, "User not found")
                return
            }

            user.uid = getFirebaseUser()?.uid
            val bitmap = user.bitmapImg
            user.bitmapImg = null

            val db = Firebase.firestore

            db.collection("users").document(fbUser.uid).set(user)
                .addOnSuccessListener {
                    if (bitmap != null) {
                        addImageToFirebase(bitmap, { onSuccess() }, { msg -> onFailure(msg) })
                    }
                }

                .addOnFailureListener { e ->
                    fbUser.delete()
                    Log.w(ContentValues.TAG, "Error adding document", e)
                    onFailure(e.message.toString())
                }


        }


        private fun addImageToFirebase(
            bitmap: Bitmap,
            onSuccess: (() -> Unit),
            onFailure: ((msg: String) -> Unit)
        ) {
            val fbUser = Firebase.auth.currentUser


            if (fbUser == null) {
                Log.i(TAG, "User is not signed in.")
                return
            }

            val ref = FirebaseStorage.getInstance().getReference("/images/${fbUser.uid}")

            if (ref == null) {
                Log.i(TAG, "Collection path not found.")
                return

            }

            val bytArr = ByteArrayOutputStream()
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bytArr)
            val data = bytArr.toByteArray()


            ref.putBytes(data)
                .addOnSuccessListener {
                    onSuccess()
                }

                .addOnFailureListener {
                    fbUser.delete()
                    onFailure(it.message.toString())

                }

        }


        fun login(
            activity: Activity,
            email: String,
            password: String,
            onSuccess: (() -> Unit)?,
            onFail: ((msg: String) -> Unit)?

        ) {

            val auth = Firebase.auth

            auth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(activity) { task ->
                    if (task.isSuccessful) {
                        if (Firebase.auth.currentUser != null) {
                            Log.d(ContentValues.TAG, "signInWithEmail:success")

                        }

                        if (onSuccess != null) {
                            onSuccess()
                        }


                    } else {
                        Log.w(ContentValues.TAG, "signInWithEmail:failure", task.exception)
                        if (onFail != null) {
                            onFail(task.exception?.message.toString())
                        }

                    }
                }

        }


        fun getUserProfile(onDataChange: (snapshot: DocumentSnapshot) -> Unit) : ListenerRegistration? {

            val fbUser = Firebase.auth.currentUser

            if (fbUser == null) {
                Log.i(TAG, "User is not logged in..")
                return null
            }


            val db = Firebase.firestore
            val docRef = db.collection("users").document(fbUser.uid)


            val registration = docRef.addSnapshotListener { snapshot, e ->

                if (e != null) {
                    Log.w(TAG, "Listen failed.", e)
                    return@addSnapshotListener
                }


                if (snapshot != null && snapshot.exists()) {
                    onDataChange(snapshot)
                    Log.d(TAG, "snapshot found")

                } else {
                    Log.d(TAG, "Current data: null")
                }
            }

            return registration
        }


        suspend fun isUserProfileCreated(): Boolean {
            val fbUser = Firebase.auth.currentUser
            if (fbUser == null) {
                Log.i(TAG, "User is not logged in..")
                return false
            }

            val db = Firebase.firestore
            val docRef = db.collection("users").document(fbUser.uid)
            val result = docRef.get()
                .await()
            if (result.data != null) {
                Log.d(TAG, result.data.toString())
                return true
            } else return false
        }


        fun getUserPicFromDB(updateUI: (it: ByteArray?) -> Unit) {

            val fbUser = Firebase.auth.currentUser

            if (fbUser == null) {
                Log.i(TAG, "User is null")
                return
            }

            val ref = FirebaseStorage.getInstance().getReference("/images/${fbUser.uid}")
            val ONE_MEGABYTE: Long = (1824 * 1824).toLong()

            ref.getBytes(ONE_MEGABYTE).addOnSuccessListener {
                updateUI(it)
            }


        }


        /**
         *  getUpdate()
         *
         */

        fun updateUserFieldInDB(
            collectionPath: String,
            strArr: ArrayList<String>,
            onSuccess: (() -> Unit)?,
            onFail: (() -> Unit)?
        ) {

            val fbUser = Firebase.auth.currentUser

            if (fbUser == null) {
                Log.i(TAG, "user is null....")
                return
            }

            val db = Firebase.firestore

            when (strArr.size) {
                2 -> {
                    db.collection(collectionPath).document(fbUser.uid).update(strArr[0], strArr[1])
                        .addOnSuccessListener {
                            if (onSuccess != null) {
                                onSuccess()
                            }
                        }
                        .addOnFailureListener {
                            if (onFail != null) {
                                onFail()
                            }
                        }
                }


                4 -> {
                    db.collection(collectionPath).document(fbUser.uid)
                        .update(strArr[0], strArr[1], strArr[2], strArr[3])
                        .addOnSuccessListener {
                            if (onSuccess != null) {
                                onSuccess()
                            }
                        }
                        .addOnFailureListener {
                            if (onFail != null) {
                                onFail()
                            }
                        }
                }

                6 -> {
                    db.collection(collectionPath).document(fbUser.uid)
                        .update(strArr[0], strArr[1], strArr[2], strArr[3], strArr[4], strArr[5])
                        .addOnSuccessListener {
                            if (onSuccess != null) {
                                onSuccess()
                            }
                        }
                        .addOnFailureListener {
                            if (onFail != null) {
                                onFail()
                            }
                        }
                }
            }

        }
        /* TODO: Fetches a users messages from the database */

        fun fetchMessages() {
            val ref = FirebaseDatabase.getInstance().getReference("")
            ref.addListenerForSingleValueEvent(object: ValueEventListener{
                override fun onDataChange(snapshot: DataSnapshot) {
                   TODO(reason = "Not yet implemented")
                }

                override fun onCancelled(error: DatabaseError) {
                    TODO("Not yet implemented")
                }

            })
        }





        fun updateUserPicInDB(bitmap: Bitmap, onSuccess: (() -> Unit)?, onFail: (() -> Unit)?) {

            val fbUser = Firebase.auth.currentUser

            if (fbUser == null) {
                Log.i(TAG, "User is not sign in.")
                return
            }
            val ref = FirebaseStorage.getInstance().getReference("/images/${fbUser.uid}")
            val bytArr = ByteArrayOutputStream()
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bytArr)
            val data = bytArr.toByteArray()

            ref.putBytes(data)
                .addOnSuccessListener {

                    val ONE_MEGABYTE: Long = (1824 * 1824).toLong()
                    ref.getBytes(ONE_MEGABYTE).addOnSuccessListener {
                        PersonalProfileVM.setUserPicUI(it)
                    }

                    if (onSuccess != null) {
                        onSuccess()
                    }

                }

                .addOnFailureListener {
                    if (onFail != null) {
                        onFail()
                    }

                }

        }





        fun getAndListenHotSpotsDB(
            onSuccess: ((hotSpots: ArrayList<HotSpot>) -> Unit)?,
            onFailure: ((msg: String) -> Unit)?
        ) : ListenerRegistration {

            val db = Firebase.firestore
            val colRef = db.collection("hotSpots3")

            val registration = colRef.addSnapshotListener { snapshot, e ->

                if (e != null) {
                    Log.w(TAG, "Listen failed.", e)
                    return@addSnapshotListener
                }

                if (snapshot != null) {

                    val hotSpots: ArrayList<HotSpot> = ArrayList()
                    snapshot.forEach { crrHotspot ->
                        val hotSpot = crrHotspot.toObject<HotSpot>()
                        hotSpots.add(hotSpot)
                    }

                    if (onSuccess != null) {
                        onSuccess(hotSpots)
                    }

                } else {
                    Log.d(TAG, "Current data: null")
                }
            }
            return registration
        }






        fun getAndListenCheckedInIds(
            hotSpotId: String,
            onSuccess: ((checkedIn: ArrayList<CheckedInDB>) -> Unit) ): ListenerRegistration {
            val db = Firebase.firestore
            val registration = db.collection("hotSpots3").document(hotSpotId).collection("checkedIn")
                .addSnapshotListener { value, error ->

                    if (error != null) {
                        //Log.w(TAG, "Listen failed.", error)
                        return@addSnapshotListener
                    }

                    if (value != null) {
                        val checkedIns = ArrayList<CheckedInDB>()
                        value.forEach {

                            val checkedIn = it.toObject<CheckedInDB>()
                            checkedIn.id = it.id

 /*                           if (checkedIn.id == null) {
                                checkedIn.id = it.id
                            }*/

                            checkedIns.add(checkedIn)
                        }
                        onSuccess(checkedIns)

                    } else {
                        Log.d(TAG, "Current data: null")
                    }
                }

            return registration
        }


        fun getCheckedInUserFromDB(
            usersId: String,
            checkedInDB: CheckedInDB,
            onSuccess: ((user: User, checkedIn: CheckedInDB) -> Unit)

        ) {
            var user: User? = null
           // var bitmap: Bitmap? = null

            val ref = FirebaseStorage.getInstance().getReference("/images/${usersId}")
            val ONE_MEGABYTE: Long = (1824 * 1824).toLong()
            ref.getBytes(ONE_MEGABYTE).addOnSuccessListener {
                var bitmap = BitmapFactory.decodeByteArray(it, 0, it.size)

                CoroutineScope(IO).launch {
                    while (user == null) {
                        delay(10)
                    }

                    CoroutineScope(Main).launch {
                        user?.let { userTem ->
                            userTem.bitmapImg = bitmap
                            onSuccess(userTem, checkedInDB)
                        }
                    }
                }
            }


            val db = Firebase.firestore
            db.collection("users").document(usersId)
                .get()
                .addOnSuccessListener { doc ->
                    doc.toObject<User>()?.apply {

                        this.uid = usersId
                        user = this

                    }
                }
        }



        fun updateIsInterestedDB(hotSpotId: String, useId: String, isInterested: Boolean) {
            val db = Firebase.firestore
            db.collection("hotSpots3").document(hotSpotId).collection("checkedIn").document(useId)
                .update("interested", isInterested)
                .addOnSuccessListener {
                    Log.d(TAG, "Success...Repository")
                }
        }

        fun getPhoneNumber(): String?{
            val phoneNumber = Firebase.auth.currentUser?.phoneNumber
            return phoneNumber
        }

        fun updateIsUserCheckedIn(hotSpotId: String){
            val db = Firebase.firestore
            val fbUser = Firebase.auth.currentUser!!

            db.collection("users").document(fbUser.uid).update("isUserCheckedIn", hotSpotId)
        }

    }







}


