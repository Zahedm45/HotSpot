package com.example.hotspot.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.hotspot.databinding.ActivityLoginBinding
import com.example.hotspot.viewModel.DataHolder
import com.example.hotspot.viewModel.LoginActivityVM
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase


class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding
    private lateinit var auth: FirebaseAuth
    private lateinit var loginActivityMV: LoginActivityVM
    private val repository = DataHolder.repository







    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
       // setContentView(R.layout.activity_login)

        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        auth = Firebase.auth

        DataHolder

        loginActivityMV = LoginActivityVM(this, binding, repository)

        binding.activityLoginCreateProfileBtn.setOnClickListener {
            val intent = Intent(this, CreateProfileActivity::class.java)
            startActivity(intent)
        }

        binding.activityLoginLoginBtn.setOnClickListener {

            DataHolder.showProgress(this)
            loginActivityMV.login(auth)


        }


    }




}



