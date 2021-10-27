package com.example.hotspot

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.Window
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import com.example.hotspot.databinding.ActivityMainBinding
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
/*        binding = ActivityMainBinding.inflate(LayoutInflater)
        setContentView(binding.root)*/




//        val afterCheckIn = AfterCheckIn()
//        supportFragmentManager.beginTransaction().replace(R.id.nav_host_fragment, afterCheckIn).commit()
//        supportActionBar?.hide()




        val beforeCheckIn = BeforeCheckIn()
        supportFragmentManager.beginTransaction().replace(R.id.nav_host_fragment, beforeCheckIn).commit()
        supportActionBar?.hide()


    }
}
