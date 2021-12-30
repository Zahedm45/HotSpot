package com.example.hotspot.Repository

import android.app.Activity
import android.app.ProgressDialog
import android.content.ContentValues
import android.net.Uri
import android.renderscript.ScriptGroup
import android.util.Log
import android.widget.Toast
import com.example.hotspot.databinding.ActivityCreateProfileBinding
import com.example.hotspot.model.User
import com.example.hotspot.model.UserProfile
import com.example.hotspot.view.CreateProfileActivity
import com.example.hotspot.viewModel.DataHolder
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class Repository2 {
    var progressDialog: ProgressDialog? = null
    val auth = Firebase.auth
    var uri: Uri? = null

    fun createUserInFirebase(
        activity: Activity,
        user: User,
        uri: Uri?,

        ) {

//
//        progressDialog = ProgressDialog(activity)
//        progressDialog!!.setTitle("Please wait")
//        progressDialog!!.setMessage("Loading ...")
//        progressDialog!!.show()
//
//
//        val baseContext = activity.baseContext
//        // var userID: String? = null
//
//        val email = user.emailAddress
//        val password = user.password
//
//        if(email.isBlank() || password.isBlank()) {
//            progressDialog!!.dismiss()
//            return
//        }
//
//        auth.createUserWithEmailAndPassword(email, password)
//            .addOnCompleteListener { task ->
//
//                if (task.isSuccessful) {
//                    this.uri = uri
//                    addProfileToFirebase(activity, user)
//
//
//                } else {
//                    progressDialog!!.dismiss()
//                    Log.w(ContentValues.TAG, "createUserWithEmail:failure", task.exception)
//                    Toast.makeText(baseContext, "Authentication failed.", Toast.LENGTH_SHORT).show()
//
//
//                }
//            }
//
//    }
//
//
//
//
//
//    private fun addProfileToFirebase(
//        activity: Activity,
//        user: User
//        ) {
//
//        val uid = fbUser.uid
//
//        val userName = binding.activityCreateProfileUsername.text.toString()
//        val name = binding.activityCreateProfileName.text.toString()
//        val password = binding.activityCreateProfilePassword.text.toString()
//        val repeatPassword = binding.activityCreateProfileRepeatPassword.text.toString()
//        val email = binding.activityCreateProfileEmailinput.text.toString()
//        val age = binding.activityCreateProfileAge.text.toString()
//        val bio = binding.activityCreateProfileBio.text.toString()
//        val women = binding.activityCreateProfileWomen
//        val men = binding.activityCreateProfileMen
//        var gender: String = "Non"
//        val img = uri
//
//        if (women.isChecked) {
//            gender = women.text.toString()
//
//        } else if (men.isChecked) {
//            gender = men.text.toString()
//        }
//
//        val user = UserProfile(
//            name = name,
//            age = age.toInt(),
//            emailAddress = email,
//            userName = userName,
//            bio = bio,
//            gender = gender
//
//        )
//
//
//        val baseContext = createProfileActivity.baseContext
//
//        db.collection("users").document(uid).set(user)
//            .addOnSuccessListener {
//                addImageToFirebase(fbUser, createProfileActivity)
//            }
//
//            .addOnFailureListener {e ->
//                if (fbUser != null) {
//                    DataHolder.fbUser = null
//                    fbUser.delete()
//                }
//                progressDialog?.dismiss()
//                Log.w(ContentValues.TAG, "Error adding document", e)
//                Toast.makeText(baseContext, "Profile creation failed! ", Toast.LENGTH_SHORT).show()
//
//            }

    }

}