package com.example.hotspot.view.CreateProfile

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.example.hotspot.R
import com.example.hotspot.databinding.ActivityCreateProfileV2Binding
import com.google.firebase.auth.FirebaseAuth

class ActivityCreateProfile : AppCompatActivity() {

    private lateinit var binding: ActivityCreateProfileV2Binding

    private lateinit var navController: NavController

    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()
        binding = ActivityCreateProfileV2Binding.inflate(layoutInflater)


        val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment_content_main)
                as NavHostFragment
        navController = navHostFragment.navController
        setContentView(binding.root)
    }



}