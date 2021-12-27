package com.example.hotspot.Repository

import android.content.ContentValues
import android.content.Intent
import android.util.Log
import android.widget.Toast
import com.example.hotspot.databinding.ActivityCreateProfileBinding
import com.example.hotspot.model.User
import com.example.hotspot.view.AfterLoginActivity
import com.example.hotspot.view.CreateProfileActivity
import com.example.hotspot.viewModel.DataHolder
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.FirebaseFirestore

class Repository {


    fun createProfileInFirebase(
        createProfileActivity: CreateProfileActivity,
        binding: ActivityCreateProfileBinding,
        auth: FirebaseAuth,
        db: FirebaseFirestore

    ) {

        val baseContext = createProfileActivity.baseContext
       // var userID: String? = null

        val email = binding.activityCreateProfileEmailinput.text.toString()
        val password = binding.activityCreateProfilePassword.text.toString()

        if (!email.isNullOrBlank() && !password.isNullOrEmpty()) {

            auth.createUserWithEmailAndPassword(email, password)

                .addOnCompleteListener { task ->

                    if (task.isSuccessful) {
                        addProfileToDb(binding, db, auth.currentUser!!, createProfileActivity)

                    }  else {
                        Log.w(ContentValues.TAG, "createUserWithEmail:failure", task.exception)
                        Toast.makeText(baseContext, "Authentication failed.", Toast.LENGTH_SHORT).show()

                    }
                }
        }
    }

    fun addProfileToDb(
        binding: ActivityCreateProfileBinding,
        db: FirebaseFirestore,
        fbUser: FirebaseUser,
        createProfileActivity: CreateProfileActivity

    ) {

        val uid = fbUser.uid

        val userName = binding.activityCreateProfileUsername.text.toString()
        val name = binding.activityCreateProfileName.text.toString()
        val password = binding.activityCreateProfilePassword.text.toString()
        val repeatPassword = binding.activityCreateProfileRepeatPassword.text.toString()
        val email = binding.activityCreateProfileEmailinput.text.toString()
        val age = binding.activityCreateProfileAge.text.toString()
        val bio = binding.activityCreateProfileBio.text.toString()
        val women = binding.activityCreateProfileWomen.text.toString()
        val men = binding.activityCreateProfileMen.text.toString()
        var gender: String = "Non"

        if (!women.isNullOrBlank()) {
            gender = women

        } else if (!men.isNullOrBlank()) {
            gender = men
        }


        val user = User(
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

                DataHolder.fbUser = fbUser
                Toast.makeText(baseContext, "Profile is successfully created! ", Toast.LENGTH_SHORT).show()
                val intent = Intent(createProfileActivity, AfterLoginActivity::class.java)
                createProfileActivity.startActivity(intent)
            }

            .addOnFailureListener {e ->
                Log.w(ContentValues.TAG, "Error adding document", e)
                Toast.makeText(baseContext, "Profile creation failed! ", Toast.LENGTH_SHORT).show()


            }

    }









}
