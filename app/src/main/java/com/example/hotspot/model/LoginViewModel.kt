package com.example.hotspot.model

import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth

class LoginViewModel : ViewModel() {

 //   private lateinit var auth: FirebaseAuth

//
//    fun createUser(
//        binding: FragmentCreateProfileBinding,
//        auth: FirebaseAuth,
//        createProfileActivity: CreateProfileActivity
//    ) {
//
//        val email = binding.createProfileEmailinput.text.toString()
//        val password = binding.createProfilePassword.text.toString()
//
////        auth.createUserWithEmailAndPassword(email, password)
////            .addOnCompleteListener(createProfile) { task ->
////                if (task.isSuccessful) {
////                    // Sign in success, update UI with the signed-in user's information
////                    Log.d(TAG, "createUserWithEmail:success")
////                    val user = auth.currentUser
////                    updateUI(user)
////                } else {
////                    // If sign in fails, display a message to the user.
////                    Log.w(TAG, "createUserWithEmail:failure", task.exception)
////                    Toast.makeText(baseContext, "Authentication failed.",
////                        Toast.LENGTH_SHORT).show()
////                    updateUI(null)
////                }
////            }
//
//    }













    //private lateinit var database: DatabaseReference
//    val db = Firebase.firestore

//    fun createUser(binding: FragmentCreateProfileBinding) {
//
//        Log.i(TAG, "login called...")
////        val userName = binding.createProfileUsername.text.toString()
////        val name = binding.createProfileName.text.toString()
////        val password = binding.createProfilePassword.text.toString()
////        val repeatPassword  = binding.createProfileRepeatPassword.text.toString()
////        val email = binding.createProfileEmailinput.text.toString()
////
////        Log.i(TAG, "login called... $userName. ")
//
//
//
//        val userName = "aaa"
//        val name = "bbb"
//        val password = "ccc"
//        val repeatPassword  = "dddd"
//        val email = "eee"
//        val age = 20
//
//        val user = User(name = name, age = age, emailAddress = email, userName = userName, password = password)
//
//        //val myRef = database.getReference("users")
//
//        db.collection("users")
//            .add(user)
//            .addOnSuccessListener { documentReference ->
//                Log.d(TAG, "DocumentSnapshot added with ID: ${documentReference.id}")
//            }
//            .addOnFailureListener { e ->
//                Log.w(TAG, "Error adding document", e)
//            }
//
//
//
//    }



}