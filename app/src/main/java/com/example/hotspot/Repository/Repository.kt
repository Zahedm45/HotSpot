package com.example.hotspot.Repository

import android.app.Activity
import android.app.ProgressDialog
import android.content.ContentValues
import android.content.ContentValues.TAG
import android.graphics.Bitmap
import android.util.Log
import android.widget.Toast
import com.example.hotspot.model.User
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import java.io.ByteArrayOutputStream

class Repository {


    companion object {

        private var progressDialog: ProgressDialog? = null
        private val auth = Firebase.auth
        private val db = Firebase.firestore
        private val fbUser = Firebase.auth.currentUser


        // this function is consist of two other functions, which are addProfileToFirebase() and addImageToFirebase()
        fun createUserInFirebase(
            activity: Activity,
            user: User,
            onSuccess: (() -> Unit)?,
            onFail: (() -> Unit)?

        ) {



            // var userID: String? = null

            val email = user.emailAddress
            val password = user.password

            if(email.isBlank() || password.isBlank()) {
                return
            }


            progressDialog = ProgressDialog(activity)
            progressDialog!!.setTitle("Please wait")
            progressDialog!!.setMessage("Loading ...")
            progressDialog!!.show()

            val baseContext = activity.baseContext

            auth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener { task ->

                    if (task.isSuccessful) {
                        addProfileToFirebase(activity, user, onSuccess, onFail)

                    } else {
                        progressDialog!!.dismiss()
                        Log.w(ContentValues.TAG, "createUserWithEmail:failure", task.exception)
                        Toast.makeText(baseContext, "Authentication failed.", Toast.LENGTH_SHORT).show()
                    }
                }

        }





        private fun addProfileToFirebase(
            activity: Activity,
            user: User,
            onSuccess: (() -> Unit)?,
            onFail: (() -> Unit)?

        ) {



            val baseContext = activity.baseContext

            if (fbUser != null) {
                Log.w(ContentValues.TAG, "User not found")

                val bitmap = user.bitmapImg
                user.bitmapImg = null
//                user.imgUri = null


                db.collection("users").document(fbUser.uid).set(user)
                    .addOnSuccessListener {
                        if (bitmap != null) {
                            addImageToFirebase(bitmap, onSuccess, onFail)
                        }
                    }

                    .addOnFailureListener {e ->
                        fbUser.delete()
                        progressDialog?.dismiss()
                        Log.w(ContentValues.TAG, "Error adding document", e)
                        Toast.makeText(baseContext, "Profile creation failed! ", Toast.LENGTH_SHORT).show()

                    }
            }

        }



        private fun addImageToFirebase(bitmap: Bitmap, onSuccess: (() -> Unit)?, onFail: (() -> Unit)? ) {

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
                    progressDialog?.dismiss()

                    if (onSuccess != null) {
                        onSuccess()
                    }
                }

                .addOnFailureListener {
                    fbUser.delete()
                    progressDialog?.dismiss()

                    if (onFail != null) {
                        onFail()
                    }

                }

        }






        fun login(
            activity: Activity,
            email: String,
            password: String,
            onSuccess: (() -> Unit)?,
            onFail: (() -> Unit)?

        ) {



            val pd = ProgressDialog(activity)
            pd.setTitle("Please wait")
            pd.setMessage("Loading ...")
            pd.show()


            auth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(activity) { task ->
                    if (task.isSuccessful) {
                        Log.d(ContentValues.TAG, "signInWithEmail:success")
                        if (onSuccess != null) {
                            pd.dismiss()
                            onSuccess()
                        }


                    } else {
                        //  pd.dismiss()
                        Log.w(ContentValues.TAG, "signInWithEmail:failure", task.exception)
                        if (onFail != null) {
                            pd.dismiss()
                            onFail()
                        }

                    }
                }

        }






        fun getUserProfile(onDataChange: (snapshot: DocumentSnapshot) -> Unit) {

            if (fbUser == null) {
                Log.i(TAG, "User is not logged in..")
                return
            }


            val docRef = db.collection("users").document(fbUser.uid)

            docRef.addSnapshotListener { snapshot, e ->

                if (e != null) {
                    Log.w(TAG, "Listen failed.", e)
                    return@addSnapshotListener
                }


                if (snapshot != null && snapshot.exists()) {
 //                   Log.d(TAG, "Current data: ${snapshot.data}")
                    onDataChange(snapshot)

                } else {
                    Log.d(TAG, "Current data: null")
                }
            }
        }



        fun getUserPicFromDB(updateUI: (it: ByteArray?) -> Unit) {
            if(fbUser == null) {
                return
            }

            val ref = FirebaseStorage.getInstance().getReference("/images/${fbUser.uid}")
            val ONE_MEGABYTE: Long = 1024 * 1024

            ref.getBytes(ONE_MEGABYTE).addOnSuccessListener {
                updateUI(it)
            }


        }


    }







}