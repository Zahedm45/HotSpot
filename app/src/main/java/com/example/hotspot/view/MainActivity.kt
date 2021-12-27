package com.example.hotspot.view

import android.annotation.SuppressLint
import android.app.ProgressDialog
import android.content.ContentValues
import android.content.ContentValues.TAG
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.example.hotspot.databinding.ActivityLoginBinding
import com.example.hotspot.viewModel.DataHolder
import com.example.hotspot.viewModel.MainActivityVM
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding
    private lateinit var auth: FirebaseAuth
    private lateinit var mainActivityMV: MainActivityVM
    private val repository = DataHolder.repository







    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
       // setContentView(R.layout.activity_login)

        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        auth = Firebase.auth

        DataHolder

        mainActivityMV = MainActivityVM(this, binding, repository)

        binding.activityLoginCreateProfileBtn.setOnClickListener {
            val intent = Intent(this, CreateProfileActivity::class.java)
            startActivity(intent)
        }

        binding.activityLoginLoginBtn.setOnClickListener {
            DataHolder.showProgress(this)
            mainActivityMV.login(auth)


        }


    }




}



