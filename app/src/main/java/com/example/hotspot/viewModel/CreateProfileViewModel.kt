package com.example.hotspot.viewModel

import android.content.Intent
import androidx.lifecycle.ViewModel
import com.example.hotspot.Repository.Repository
import com.example.hotspot.databinding.ActivityCreateProfileBinding
import com.example.hotspot.view.AfterLoginActivity
import com.example.hotspot.view.CreateProfileActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.FirebaseFirestore

class CreateProfileViewModel(private val repository: Repository) : ViewModel() {



    fun createNewProfile(
        createProfileActivity: CreateProfileActivity,
        binding: ActivityCreateProfileBinding,
        auth: FirebaseAuth,
        db: FirebaseFirestore
    ){


        repository.createProfileInFirebase(createProfileActivity, binding, auth, db) { fbUser ->
          add(fbUser)
        }


    }


    fun add(fbUser: FirebaseUser) : FirebaseUser{
        return fbUser
    }




//    val intent = Intent(createProfileActivity, AfterLoginActivity::class.java)
//    createProfileActivity.startActivity(intent)
}