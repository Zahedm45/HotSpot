package com.example.hotspot.view

import android.annotation.SuppressLint
import android.app.ProgressDialog
import android.content.ContentValues
import android.content.ContentValues.TAG
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.example.hotspot.databinding.ActivityLoginBinding
import com.example.hotspot.viewModel.DataHolder
import com.example.hotspot.viewModel.MainActivityVM
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding
    private lateinit var auth: FirebaseAuth
    private lateinit var mainActivityMV: MainActivityVM
    private val repository = DataHolder.repository







    @SuppressLint("ResourceType")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
       // setContentView(R.layout.activity_login)

        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        auth = Firebase.auth

        DataHolder

        mainActivityMV = MainActivityVM(this, binding, repository)

        binding.activityLoginCreateProfileBtn.setOnClickListener {
            val intent = Intent(this, CreateProfileActivity::class.java)
            startActivity(intent)
        }

        binding.activityLoginLoginBtn.setOnClickListener {
            DataHolder.showProgress(this)
            mainActivityMV.login(auth)


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