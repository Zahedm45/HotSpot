package com.example.hotspot.view

import android.content.Intent
import android.os.Bundle
import android.os.PersistableBundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.example.hotspot.R
import com.example.hotspot.databinding.ActivityAfterLoginBinding
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class AfterLoginActivity: AppCompatActivity() {
    private lateinit var binding: ActivityAfterLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityAfterLoginBinding.inflate(layoutInflater)

        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_after_login)
        val btn = findViewById<BottomNavigationView>(R.id.bottomNavigationView)
        val navCont = findNavController(R.id.nav_host_fragment)
        btn.setupWithNavController(navCont)
    }





}