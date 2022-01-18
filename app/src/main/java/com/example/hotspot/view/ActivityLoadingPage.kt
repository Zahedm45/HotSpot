package com.example.hotspot.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.navigation.findNavController
import com.example.hotspot.R
import com.example.hotspot.databinding.ActivityLoadingPageBinding
import com.example.hotspot.databinding.ActivityLoginSuggestionBinding
import com.example.hotspot.other.Constants
import com.example.hotspot.other.network.TAG
import com.example.hotspot.repository.Repository
import com.example.hotspot.view.createProfilePackage.ActivityCreateProfile
import com.example.hotspot.viewModel.DataHolder
import com.example.hotspot.viewModel.LoadingPageVM
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

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
        val intent = Intent(this@ActivityLoadingPage, LoginActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
        startActivity(intent)
        finish()
    }

    private fun toMapsActivity(){

        val intent = Intent(this@ActivityLoadingPage, AfterLoginActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
        startActivity(intent)
        finish()
    }
}
