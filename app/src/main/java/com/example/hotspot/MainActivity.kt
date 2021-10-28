package com.example.hotspot

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.ContactsContract
import com.example.hotspot.databinding.ActivityMainBinding

import android.view.LayoutInflater
import android.view.Window
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
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


        var botmNavView: BottomNavigationView = findViewById(R.id.bottomNavigationView)


        botmNavView.setOnNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.fragment_home -> replaceFragment(afterCheckIn)
                R.id.fragment_favorites -> replaceFragment(favorite)
                R.id.fragment_Profile -> replaceFragment(beforeCheckIn)
                R.id.fragment_message -> replaceFragment(favorite)



            }
            true
        }
        replaceFragment(beforeCheckIn)


    }


    private fun replaceFragment(fragment: Fragment) {

        if (fragment != null) {

            supportFragmentManager.beginTransaction().replace(R.id.nav_host_fragment, fragment).commit()

        }
    }

}




