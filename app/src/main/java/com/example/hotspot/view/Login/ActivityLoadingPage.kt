package com.example.hotspot.view.Login

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import com.example.hotspot.databinding.ActivityLoadingPageBinding
import com.example.hotspot.view.ActivityAfterLogin
import com.example.hotspot.viewModel.LoadingPageVM

class ActivityLoadingPage : AppCompatActivity() {
    private lateinit var binding: ActivityLoadingPageBinding
    private val viewModel : LoadingPageVM by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoadingPageBinding.inflate(layoutInflater)
        supportActionBar?.hide()

        isUserLoggedIn()
        setContentView(binding.root)
    }

    private fun isUserLoggedIn() {
        viewModel.checkForCurrentuser(
            {toMapsActivity()},
            {navigateToLogin()}
        )
    }

    private fun navigateToLogin(){
        val intent = Intent(this@ActivityLoadingPage, ActivityLogin::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
        startActivity(intent)
        finish()
    }

    private fun toMapsActivity(){

        val intent = Intent(this@ActivityLoadingPage, ActivityAfterLogin::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
        startActivity(intent)
        finish()
    }
}
