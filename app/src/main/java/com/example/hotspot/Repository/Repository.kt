package com.example.hotspot.Repository

import android.app.Activity
import android.app.ProgressDialog
import android.content.ContentValues
import android.content.ContentValues.TAG
import android.content.Intent
import android.graphics.BitmapFactory
import android.net.Uri
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import com.example.hotspot.databinding.ActivityCreateProfileBinding
import com.example.hotspot.databinding.ActivityLoginBinding
import com.example.hotspot.model.UserProfile
import com.example.hotspot.view.AfterLoginActivity
import com.example.hotspot.view.CreateProfileActivity
import com.example.hotspot.view.LoginActivity
import com.example.hotspot.viewModel.DataHolder
import com.example.hotspot.viewModel.PersonalProfileViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import java.io.File

class Repository {

    val db = Firebase.firestore
    var uri: Uri? = null
    var progressDialog: ProgressDialog? = null

    fun createUserInFirebase(
        createProfileActivity: CreateProfileActivity,
        binding: ActivityCreateProfileBinding,
        auth: FirebaseAuth,
        uri: Uri?,

        ) {


        progressDialog = ProgressDialog(createProfileActivity)
        progressDialog!!.setTitle("Please wait")
        progressDialog!!.setMessage("Loading ...")
        progressDialog!!.show()


        val baseContext = createProfileActivity.baseContext
       // var userID: String? = null

        val email = binding.activityCreateProfileEmailinput.text.toString()
        val password = binding.activityCreateProfilePassword.text.toString()

        if(email.isBlank() || password.isBlank()) {
            progressDialog!!.dismiss()
            return
        }

        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->

                if (task.isSuccessful) {
                    this.uri = uri
                    addProfileToFirebase(binding, auth.currentUser!!, createProfileActivity)


                } else {
                    progressDialog!!.dismiss()
                    Log.w(ContentValues.TAG, "createUserWithEmail:failure", task.exception)
                    Toast.makeText(baseContext, "Authentication failed.", Toast.LENGTH_SHORT).show()


                }
            }

    }

   private fun addProfileToFirebase(
        binding: ActivityCreateProfileBinding,
        fbUser: FirebaseUser,
        createProfileActivity: CreateProfileActivity,

    ) {

        val uid = fbUser.uid

        val userName = binding.activityCreateProfileUsername.text.toString()
        val name = binding.activityCreateProfileName.text.toString()
        val password = binding.activityCreateProfilePassword.text.toString()
        val repeatPassword = binding.activityCreateProfileRepeatPassword.text.toString()
        val email = binding.activityCreateProfileEmailinput.text.toString()
        val age = binding.activityCreateProfileAge.text.toString()
        val bio = binding.activityCreateProfileBio.text.toString()
        val women = binding.activityCreateProfileWomen
        val men = binding.activityCreateProfileMen
        var gender: String = "Non"
        val img = uri

        if (women.isChecked) {
            gender = women.text.toString()

        } else if (men.isChecked) {
            gender = men.text.toString()
        }

        val user = UserProfile(
            name = name,
            age = age.toInt(),
            emailAddress = email,
            userName = userName,
            password = password,
            bio = bio,
            gender = gender

        )


        val baseContext = createProfileActivity.baseContext

        db.collection("users").document(uid).set(user)
            .addOnSuccessListener {
                addImageToFirebase(fbUser, createProfileActivity)
            }

            .addOnFailureListener {e ->
                if (fbUser != null) {
                    DataHolder.fbUser = null
                    fbUser.delete()
                }
                progressDialog?.dismiss()
                Log.w(ContentValues.TAG, "Error adding document", e)
                Toast.makeText(baseContext, "Profile creation failed! ", Toast.LENGTH_SHORT).show()

            }

    }



    fun addImageToFirebase(fbUser: FirebaseUser, createProfileActivity: CreateProfileActivity) {

        val baseContext = createProfileActivity.baseContext

        val ref = FirebaseStorage.getInstance().getReference("/images/${fbUser.uid}")
        if (uri != null) {
            ref.putFile(uri!!)
                .addOnSuccessListener {

                    DataHolder.fbUser = fbUser
                    val msg = "Account is successfully created."
                    getUser(null, createProfileActivity, msg)
 //                   Toast.makeText(baseContext, "Profile is successfully created! ", Toast.LENGTH_SHORT).show()

                    val intent = Intent(createProfileActivity, AfterLoginActivity::class.java)
                    createProfileActivity.startActivity(intent)
                }
                .addOnFailureListener { it ->
                    if (fbUser != null) {
                        DataHolder.fbUser = null
                        fbUser.delete()
                    }
                    progressDialog?.dismiss()
                    Log.w(ContentValues.TAG, "Error uploading image to database", it)
                    Toast.makeText(baseContext, "Error uploading image to database! ", Toast.LENGTH_SHORT).show()

                }

        }

    }



     fun login(loginActivity: LoginActivity, binding: ActivityLoginBinding, auth: FirebaseAuth) {


         if( (progressDialog == null) || (progressDialog?.isShowing == false) ) {
             progressDialog = ProgressDialog(loginActivity)
             progressDialog!!.setTitle("Please wait")
             progressDialog!!.setMessage("Loading ...")
             progressDialog!!.show()
         }

         val email = binding.activityLoginEmail.text.toString()
         val password = binding.activityLoginPassword.text.toString()
         val baseContext = loginActivity.baseContext



        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(loginActivity) { task ->
                if (task.isSuccessful) {

                    DataHolder.fbUser = auth.currentUser

                    val msg = "Login successfully."
                    getUser(loginActivity, null, msg)
                    Log.d(ContentValues.TAG, "signInWithEmail:success")


                } else {
                    progressDialog?.dismiss()
                    Log.w(ContentValues.TAG, "signInWithEmail:failure", task.exception)
                    Toast.makeText(baseContext, "Authentication failed.",
                        Toast.LENGTH_SHORT).show()
                    // updateUI(null)
                }
            }

    }



    private fun getUser(loginActivity: LoginActivity?, createProfileActivity: CreateProfileActivity?, msg: String) {
        var activity: Activity? = null

        if (loginActivity != null) {
            activity = loginActivity

        } else if (createProfileActivity != null) {
            activity = createProfileActivity

        } else {
            Log.i(TAG, "getUser is being called without a parameter..")
            return
        }




        if (DataHolder.fbUser == null) {
            return
        }

        val fbUserId = DataHolder.fbUser!!.uid

        val docRef = db.collection("users").document(fbUserId)
        docRef.get()
            .addOnSuccessListener { document ->

                if (document != null) {

                    val name = document.get("name").toString()
                    val userName = document.get("userName").toString()
                    val age = document.get("age").toString().toInt()
                    val email = document.get("emailAddress").toString()
                    val password = document.get("password").toString()
                    val bio = document.get("bio").toString()
                    val gender = document.get("gender").toString()

                    val userProfile = UserProfile(name = name, age =  age, emailAddress = email,
                        userName =  userName, password =  password, bio = bio, gender = gender)

                    getUserPic(fbUserId, userProfile)
                    PersonalProfileViewModel.mutableUserProfile = MutableLiveData(userProfile)

                    progressDialog?.dismiss()
                    updateUI(activity)
                    Toast.makeText(activity.baseContext, msg, Toast.LENGTH_SHORT).show()


                } else {
                    Log.d(TAG, "No such document")
                }
            }

            .addOnFailureListener { exception ->
                Log.d(TAG, "get failed with ", exception)

            }

    }

    private fun getUserPic(fbUserId: String, userProfile: UserProfile) {
        if(fbUserId == null) {
            return
        }

        val ref = FirebaseStorage.getInstance().getReference("/images/${fbUserId}")
        val localFile = File.createTempFile("localImage", "jpeg")

        ref.getFile(localFile)
            .addOnSuccessListener { img ->

                val bitmap = BitmapFactory.decodeFile(localFile.absolutePath)
                userProfile.bitmapImg = bitmap
                PersonalProfileViewModel.mutableUserProfile = MutableLiveData(userProfile)

            }

    }


    private fun updateUI(activity: Activity) {

        val intent = Intent(activity, AfterLoginActivity::class.java)
        activity.startActivity(intent)
    }











}
