package com.example.hotspot.Repository

import android.app.Activity
import android.app.ProgressDialog
import android.content.ContentValues
import android.content.ContentValues.TAG
import android.graphics.Bitmap
import android.util.Log
import android.widget.Toast
import com.example.hotspot.model.User
import com.example.hotspot.viewModel.DataHolder
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import java.io.ByteArrayOutputStream

class Repository2 {
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


        progressDialog = ProgressDialog(activity)
        progressDialog!!.setTitle("Please wait")
        progressDialog!!.setMessage("Loading ...")
        progressDialog!!.show()


        val baseContext = activity.baseContext
        // var userID: String? = null

        val email = user.emailAddress
        val password = user.password

        if(email.isBlank() || password.isBlank()) {
            progressDialog!!.dismiss()
            return
        }

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
            user.imgUri = null


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


        if( (progressDialog == null) || (progressDialog?.isShowing == false) ) {
            progressDialog = ProgressDialog(activity)
            progressDialog!!.setTitle("Please wait")
            progressDialog!!.setMessage("Loading ...")
            progressDialog!!.show()
        }



        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(activity) { task ->
                if (task.isSuccessful) {

                    DataHolder.fbUser = auth.currentUser

                    val msg = "Login successfully."
                    Log.d(ContentValues.TAG, "signInWithEmail:success")
                    if (onSuccess != null) {
                        onSuccess()
                    }


                } else {
                    progressDialog?.dismiss()
                    Log.w(ContentValues.TAG, "signInWithEmail:failure", task.exception)
                    if (onFail != null) {
                        onFail()
                    }

                }
            }

    }


}