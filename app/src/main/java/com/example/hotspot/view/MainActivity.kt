package com.example.hotspot.view

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.hotspot.databinding.ActivityMainBinding

import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.example.hotspot.R
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding


    private val beforeCheckIn = BeforeCheckIn()
    private val messageOverview = MessageOverview()
    private val favorite = Favorites()
    private val logIn = LogIn()
    private val profile = PersonalProfile()
    private val afterCheckIn = AfterCheckIn()


    @SuppressLint("ResourceType")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

/*        binding = ActivityMainBinding.inflate(LayoutInflater)
        setContentView(binding.root)*/

        //btmNavigation = findViewById(R.id.bottomNavigationView)

        val btn = findViewById<BottomNavigationView>(R.id.bottomNavigationView)
        val navCont = findNavController(R.id.nav_host_fragment)
        btn.setupWithNavController(navCont)

//        var botmNavView: BottomNavigationView = findViewById(R.id.bottomNavigationView)
//        botmNavView.setOnNavigationItemSelectedListener {
//            when (it.itemId) {
//
//                R.id.fragment_message -> replaceFragment(beforeCheckIn)
//
//                R.id.fragment_Profile -> replaceFragment(profile)
//                R.id.fragment_favorites -> replaceFragment(favorite)
//                R.id.fragment_home -> replaceFragment(logIn)
//            }
//            true
//        }
//
//
//
//    }
//
//
//    private fun replaceFragment(fragment: Fragment) {
//
//        if (fragment != null) {
//            supportFragmentManager.beginTransaction().apply {
//                replace(R.id.nav_host_fragment, fragment)
//                commit()
//            }
//
//        }

    }






}




