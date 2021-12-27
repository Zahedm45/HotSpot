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

class CreateProfileViewModel(
    private val repository: Repository,
    val createProfileActivity: CreateProfileActivity,
    val binding: ActivityCreateProfileBinding,


) : ViewModel() {


    fun createNewProfile(auth: FirebaseAuth) {
        repository.createProfileInFirebase(createProfileActivity, binding, auth)
    }




}