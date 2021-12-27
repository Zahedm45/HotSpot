package com.example.hotspot.view

import android.app.Activity
import android.content.ContentValues
import android.content.Intent
import android.graphics.drawable.BitmapDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import com.example.hotspot.databinding.ActivityCreateProfileBinding
import com.example.hotspot.viewModel.CreateProfileViewModel
import com.example.hotspot.viewModel.DataHolder
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import java.lang.Thread.sleep

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
            DataHolder.showProgress(this)
            createProfileVM.createNewProfile(auth)

        }

        binding.activityCreateProfileImage.setOnClickListener {

            val intent = Intent(Intent.ACTION_PICK)
            intent.type = "image/*"
            startActivityForResult(intent, 0)
        }
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == 0 && resultCode == Activity.RESULT_OK && data != null) {

            val uri = data.data
            val bitMap = MediaStore.Images.Media.getBitmap(contentResolver, uri)
            val bitMDrawable = BitmapDrawable(bitMap)
            binding.activityCreateProfileImage.setBackgroundDrawable(bitMDrawable)

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