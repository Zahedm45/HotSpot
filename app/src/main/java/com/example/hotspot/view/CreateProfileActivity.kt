package com.example.hotspot.view

import android.content.ContentValues.TAG
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.example.hotspot.databinding.ActivityCreateProfileBinding
import com.example.hotspot.model.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class CreateProfileActivity : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth
    val db = Firebase.firestore


    private lateinit var binding: ActivityCreateProfileBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityCreateProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)
//        setContentView(R.layout.activity_create_profile)
        auth = Firebase.auth


        binding.createprofileBtn.setOnClickListener {
            val email = binding.activityCreateProfileEmailinput.text.toString()
            val password = binding.activityCreateProfilePassword.text.toString()

            if (!email.isNullOrBlank() && !password.isNullOrEmpty()) {
                createAccount(email, password)
            }

        }
    }


    override fun onStart() {
        super.onStart()

        val currentUser = auth.currentUser
        if(currentUser != null){
       //     reload();
        }
    }


    lateinit var uid : String


    private fun createAccount(email: String, password: String) {
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    Log.d(TAG, "createUserWithEmail:success")
                    val user = auth.currentUser

                    if (user != null) {
                        uid = user.uid
                    }
                    updateUI(user)

                } else {
                    // If sign in fails, display a message to the user.
                    Log.w(TAG, "createUserWithEmail:failure", task.exception)
                    Toast.makeText(baseContext, "Authentication failed.",
                        Toast.LENGTH_SHORT).show()
                  //  updateUI(null)
                }
            }


    }

    private fun updateUI(user: FirebaseUser?) {
        addToFirebase(binding)



    }


    fun addToFirebase(binding: ActivityCreateProfileBinding) {

        val userName = binding.activityCreateProfileUsername.text.toString()
        val name = binding.activityCreateProfileName.text.toString()
        val password = binding.activityCreateProfilePassword.text.toString()
        val repeatPassword = binding.activityCreateProfileRepeatPassword.text.toString()
        val email = binding.activityCreateProfileEmailinput.text.toString()
        val age = binding.activityCreateProfileAge.text.toString()
        val bio = binding.activityCreateProfileBio.text.toString()
        val women = binding.activityCreateProfileWomen.text.toString()
        val men = binding.activityCreateProfileMen.text.toString()
        var gender: String = "0"

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



        db.collection("users").document(uid).set(user)
            .addOnSuccessListener {
                val intent = Intent(this, AfterLoginActivity::class.java)
                startActivity(intent)
            }

            .addOnFailureListener {e ->
                Log.w(TAG, "Error adding document", e)
            }















//            .add(user)
//            .addOnSuccessListener { documentReference ->
//                Log.d(TAG, "DocumentSnapshot added with ID: ${documentReference.id}")
//
//                val intent = Intent(this, AfterLoginActivity::class.java)
//                startActivity(intent)
//
//            }
//            .addOnFailureListener { e ->
//                Log.w(TAG, "Error adding document", e)
//            }

    }


}