package com.example.hotspot.viewModel

import android.net.Uri
import androidx.lifecycle.ViewModel
import com.example.hotspot.Repository.Repository
import com.example.hotspot.databinding.ActivityCreateProfileBinding
import com.example.hotspot.view.CreateProfileActivity
import com.google.firebase.auth.FirebaseAuth

class CreateProfileViewModel(
    private val repository: Repository,
    val createProfileActivity: CreateProfileActivity,
    val binding: ActivityCreateProfileBinding,


) : ViewModel() {


    fun createNewProfile(auth: FirebaseAuth, uri: Uri?) {


        repository.createProfileInFirebase(createProfileActivity, binding, auth, uri)
    }




}