package com.example.hotspot.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.hotspot.databinding.ActivityLoadingPageBinding
import com.example.hotspot.databinding.ActivityLoginSuggestionBinding
import com.example.hotspot.repository.Repository
import com.example.hotspot.view.createProfilePackage.ActivityCreateProfile
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ActivityLoadingPage : AppCompatActivity() {
    private val repository = Repository
    private lateinit var binding: ActivityLoadingPageBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoadingPageBinding.inflate(layoutInflater)
        supportActionBar?.hide()

        isUserLoggedIn()
        setContentView(binding.root)
    }

    private fun isUserLoggedIn() {
        val user = Firebase.auth.currentUser
        var isCreated = true
        if (user != null) {
            val abc = CoroutineScope(Dispatchers.IO).launch {
                isCreated = repository.isUserProfileCreated()
                Log.d("LUCASLOFT", isCreated.toString())
                if(isCreated) {
                    val intent = Intent(this@ActivityLoadingPage, AfterLoginActivity::class.java)
                    intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
                    startActivity(intent)
                    finish()
                }
            }.isCompleted
        }
    }
}
