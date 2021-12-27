package com.example.hotspot.view

import android.content.ContentValues
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.hotspot.databinding.ActivityCreateProfileBinding
import com.example.hotspot.viewModel.CreateProfileViewModel
import com.example.hotspot.viewModel.DataHolder
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class CreateProfileActivity : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth
    val db = Firebase.firestore

    private val repository = DataHolder.repository
    private lateinit var createProfileVM : CreateProfileViewModel

    private lateinit var binding: ActivityCreateProfileBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityCreateProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)
//        setContentView(R.layout.activity_create_profile)
        auth = Firebase.auth

        createProfileVM = CreateProfileViewModel(repository, this, binding)

        binding.createprofileBtn.setOnClickListener {
            createProfileVM.createNewProfile(auth)

        }
    }


    override fun onStart() {
        super.onStart()

//        val currentUser = auth.currentUser
//        if(currentUser != null){
//       //     reload();
//        }
    }



}