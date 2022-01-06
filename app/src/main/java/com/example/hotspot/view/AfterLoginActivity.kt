package com.example.hotspot.view

import android.content.Intent
import android.os.Bundle
import android.os.PersistableBundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavOptions
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.example.hotspot.R
import com.example.hotspot.other.Constants.ACTION_SHOW_TRACKING_FRAGMENT
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class AfterLoginActivity: AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_after_login)

        navigateToMapFragment(intent)

        val btn = findViewById<BottomNavigationView>(R.id.bottomNavigationView)
        val navCont = findNavController(R.id.nav_host_fragment)
        btn.setupWithNavController(navCont)
    }


    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
        navigateToMapFragment(intent)
    }


    private fun navigateToMapFragment(intent: Intent?) {
        if (intent?.action == ACTION_SHOW_TRACKING_FRAGMENT) {
            // Reconsideration is needed
            findNavController(R.id.nav_host_fragment).navigate(R.id.trackingFragment)
        }

    }

}