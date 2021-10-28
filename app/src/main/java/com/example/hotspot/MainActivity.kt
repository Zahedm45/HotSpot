package com.example.hotspot

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.hotspot.databinding.ActivityMainBinding

import android.view.LayoutInflater
import android.view.Window
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
/*        binding = ActivityMainBinding.inflate(LayoutInflater)
        setContentView(binding.root)*/

        // temporary coding, just to show the scenarios of before and after check in
//        val beforeCheckIn = BeforeCheckIn()
//        supportFragmentManager.beginTransaction().replace(R.id.nav_host_fragment, beforeCheckIn).commit()
//        supportActionBar?.hide()

    }
}
