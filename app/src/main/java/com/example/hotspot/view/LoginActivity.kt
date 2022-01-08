package com.example.hotspot.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.hotspot.R
import com.example.hotspot.databinding.ActivityLoginSuggestionBinding
import com.example.hotspot.view.phoneAuthentification.ActivityPhoneAuthentification


class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginSuggestionBinding




    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
       // setContentView(R.layout.activity_login)

        binding = ActivityLoginSuggestionBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.hide()

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



