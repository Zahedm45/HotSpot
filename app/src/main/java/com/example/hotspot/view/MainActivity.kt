package com.example.hotspot.view

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.ContentValues.TAG
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.example.hotspot.databinding.ActivityLoginBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding
    private lateinit var auth: FirebaseAuth





    @SuppressLint("ResourceType")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
       // setContentView(R.layout.activity_login)

        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        auth = Firebase.auth

        binding.activityLoginCreateProfileBtn.setOnClickListener {
            val intent = Intent(this, CreateProfileActivity::class.java)
            startActivity(intent)
        }

        binding.activityLoginLoginBtn.setOnClickListener {

            val email = binding.activityLoginEmail.text?.toString()
            val password = binding.activityLoginPassword.text?.toString()

            if (!email.isNullOrBlank() && !password.isNullOrEmpty()) {
                login(email, password)
            }

        }


    }




    private fun login(email: String, password: String) {
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    Log.d(TAG, "signInWithEmail:success")
                    val user = auth.currentUser
                    updateUI(user)
                } else {
                    // If sign in fails, display a message to the user.
                    Log.w(TAG, "signInWithEmail:failure", task.exception)
                    Toast.makeText(baseContext, "Authentication failed.",
                        Toast.LENGTH_SHORT).show()
                   // updateUI(null)
                }
            }

    }

    fun updateUI(user: FirebaseUser?) {

        val intent = Intent(this, AfterLoginActivity::class.java)
        startActivity(intent)

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