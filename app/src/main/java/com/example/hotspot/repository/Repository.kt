package com.example.hotspot.repository
import android.app.Activity
import android.content.ContentValues
import android.content.ContentValues.TAG
import android.graphics.Bitmap
import android.util.Log
import com.example.hotspot.model.User
import com.example.hotspot.viewModel.PersonalProfileVM
import com.google.android.gms.tasks.Tasks.await
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import kotlinx.coroutines.*
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.tasks.await
import java.io.ByteArrayOutputStream

class Repository {


    companion object {

       // private val auth = Firebase.auth
       // private val fbUser = Firebase.auth.currentUser
        //private val db = Firebase.firestore

/*
        fun createUserInFirebase(
            user: User,
            onSuccess: (() -> Unit),
            onFailure: ((message: String) -> Unit)

        ) {



            val email = user.emailAddress
            val password = user.password

            if(email.isBlank() || password.isBlank()) {
                return
            }

           val auth = Firebase.auth
            auth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener { task ->

                    if (task.isSuccessful) {
                        addProfileToFirebase(user, onSuccess, onFailure)

                    } else {

                        Log.w(ContentValues.TAG, "createUserWithEmail:failure", task.exception)
                        onFailure(task.exception?.message.toString())
                    }
                }

        }
*/ //TODO we no longer need this method.




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


            val bitmap = user.bitmapImg
            user.bitmapImg = null

            val db = Firebase.firestore

            db.collection("users").document(fbUser.uid).set(user)
                .addOnSuccessListener {
                    if (bitmap != null) {
                        addImageToFirebase(bitmap, {onSuccess()}, { msg -> onFailure(msg) })
                    }
                }

                .addOnFailureListener {e ->
                    fbUser.delete()
                    Log.w(ContentValues.TAG, "Error adding document", e)
                    onFailure(e.message.toString())
                }


        }



        private fun addImageToFirebase(bitmap: Bitmap, onSuccess: (() -> Unit), onFailure: ((msg: String) -> Unit) ) {
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
                        if(Firebase.auth.currentUser != null) {
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






        fun getUserProfile(onDataChange: (snapshot: DocumentSnapshot) -> Unit) {

            val fbUser = Firebase.auth.currentUser

            if (fbUser == null) {
                Log.i(TAG, "User is not logged in..")
                return
            }


            val db = Firebase.firestore
            val docRef = db.collection("users").document(fbUser.uid)


            docRef.addSnapshotListener { snapshot, e ->

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

        }


        suspend fun isUserProfileCreated() : Boolean{
            val fbUser = Firebase.auth.currentUser
                if (fbUser == null) {
                    Log.i(TAG, "User is not logged in..")
                    return false
                }

            val db = Firebase.firestore
            val docRef =  db.collection("users").document(fbUser.uid)
            val result = docRef.get()
                .await()
            if( result.data != null){
                Log.d(TAG, result.data.toString())
                return true
            }
            else return false
        }





        fun getUserPicFromDB(updateUI: (it: ByteArray?) -> Unit) {

            val fbUser = Firebase.auth.currentUser

            if(fbUser == null) {
                Log.i(TAG, "User is null")
                return
            }

            val ref = FirebaseStorage.getInstance().getReference("/images/${fbUser.uid}")
            val ONE_MEGABYTE: Long = (1024 * 1024).toLong()

            ref.getBytes(ONE_MEGABYTE).addOnSuccessListener {
                updateUI(it)
            }


        }








        /**
         *  getUpdate()
         *
         */

        fun updateUserFieldInDB(collectionPath: String, strArr: ArrayList<String>, onSuccess: (() -> Unit)?, onFail: (() -> Unit)?) {

            val fbUser = Firebase.auth.currentUser

            if (fbUser == null) {
                Log.i(TAG, "user is null....")
                return
            }

            val db = Firebase.firestore

            when(strArr.size) {
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
                    db.collection(collectionPath).document(fbUser.uid).update(strArr[0], strArr[1], strArr[2], strArr[3])
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
                    db.collection(collectionPath).document(fbUser.uid).update(strArr[0], strArr[1], strArr[2], strArr[3], strArr[4], strArr[5])
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






         fun updateUserPicInDB(bitmap: Bitmap, onSuccess: (() -> Unit)?, onFail: (() -> Unit)? ) {

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

                    val ONE_MEGABYTE: Long = (1024 * 1024).toLong()
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





        fun getHotSpots() {

            val db = Firebase.firestore
            val docRef = db.collection("hotSpots")

            docRef.get()
                .addOnSuccessListener {
                    if(it != null) {
                        Log.i(TAG, "hotspots..${it.documents}")
                    }



                }



        }







    }









}