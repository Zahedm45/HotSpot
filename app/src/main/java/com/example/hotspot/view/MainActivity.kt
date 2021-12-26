package com.example.hotspot.view

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.hotspot.databinding.ActivityLoginBinding


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding





    @SuppressLint("ResourceType")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
       // setContentView(R.layout.activity_login)

        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)


        binding.createProfile.setOnClickListener {
            val intent = Intent(this, CreateProfileActivity::class.java)
            startActivity(intent)
        }

        binding.activityLoginBtn.setOnClickListener {
            val intent = Intent(this, AfterLoginActivity::class.java)
            startActivity(intent)
        }

    }



}







//    private val beforeCheckIn = BeforeCheckIn()
//    private val messageOverview = MessageOverview()
//    private val favorite = Favorites()
//    private val logIn = LogIn()
//    private val profile = PersonalProfile()
//    private val afterCheckIn = AfterCheckIn()




//btmNavigation = findViewById(R.id.bottomNavigationView)







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