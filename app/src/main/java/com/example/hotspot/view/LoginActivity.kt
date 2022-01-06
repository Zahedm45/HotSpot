package com.example.hotspot.view

import android.app.ProgressDialog
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.InputType
import android.util.Log
import android.widget.EditText
import android.widget.Toast
import com.example.hotspot.R
import com.example.hotspot.databinding.ActivityLoginSuggestionBinding
import com.example.hotspot.repository.Repository
import com.example.hotspot.view.createProfilePackage.ActivityCreateProfile
import com.example.hotspot.view.phoneAuthentification.ActivityPhoneAuthentification
import com.example.hotspot.viewModel.LoginActivityVM
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch


class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginSuggestionBinding
    private lateinit var loginActivityMV: LoginActivityVM



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
       // setContentView(R.layout.activity_login)

        binding = ActivityLoginSuggestionBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.hide()

        loginActivityMV = LoginActivityVM(this)

        binding.activityLoginCreateProfileBtn.setOnClickListener {
            startPhoneAuthentication()
        }

        binding.activityLoginLoginBtn.setOnClickListener {
            startPhoneAuthentication()
        }


        binding.activityLoginForgotPassword.setOnClickListener {
            //TODO delete forgot password function, no longer necessary
           // forgotPassword()
        }


    }

    private fun startPhoneAuthentication(){
        val intentPhoneAuth = Intent(this, ActivityPhoneAuthentification::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
        startActivity(intentPhoneAuth)
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
    }
}



