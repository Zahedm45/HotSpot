package com.example.hotspot.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.navigation.findNavController
import com.example.hotspot.R
import com.example.hotspot.databinding.ActivityLoadingPageBinding
import com.example.hotspot.databinding.ActivityLoginSuggestionBinding
import com.example.hotspot.other.Constants
import com.example.hotspot.repository.Repository
import com.example.hotspot.view.createProfilePackage.ActivityCreateProfile
import com.example.hotspot.viewModel.DataHolder
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.delay
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

        if (user != null) {
            CoroutineScope(IO).launch {
                val isCreated = repository.isUserProfileCreated()
                Log.d("LUCASLOFT", isCreated.toString())
                if(isCreated) {
                    toMapsActivity() // If profile has been created, we go to our Maps landing page.
                }
                else{
                    navigateToLogin() //If no profile has been created, but user has been authed.
                }
            }.isCompleted
        }
        else{
            CoroutineScope(IO).launch{
            delay(1500)
            navigateToLogin()} // in case of no user remember by our system, we just send them to login
        }
    }

    private fun navigateToLogin(){
        val intent = Intent(this@ActivityLoadingPage, LoginActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
        startActivity(intent)
        finish()
    }

    private fun toMapsActivity(){
        DataHolder.getCurrentUserFromDB()
        val intent = Intent(this@ActivityLoadingPage, AfterLoginActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
        startActivity(intent)
        finish()
    }








//    override fun onNewIntent(intent: Intent?) {
//        super.onNewIntent(intent)
//        navigateToMapFragment(intent)
//    }
//
//
//    private fun navigateToMapFragment(intent: Intent?) {
//        if (intent?.action == Constants.ACTION_SHOW_TRACKING_FRAGMENT) {
//            // Reconsideration is needed
//            findNavController(R.id.nav_host_fragment).navigate(R.id.trackingFragment)
//        }
//
//    }
}
