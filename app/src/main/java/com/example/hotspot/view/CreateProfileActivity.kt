package com.example.hotspot.view

import android.content.ContentValues.TAG
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.example.hotspot.R
import com.example.hotspot.databinding.ActivityCreateProfileBinding
import com.example.hotspot.databinding.ActivityLoginBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class CreateProfileActivity : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth

    private lateinit var binding: ActivityCreateProfileBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityCreateProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)
//        setContentView(R.layout.activity_create_profile)
        auth = Firebase.auth


        binding.createprofileBtn.setOnClickListener {
            val emil = binding.createProfileEmailinput.text.toString()
            val password = binding.createProfilePassword.text.toString()

            createAccount(emil, password)

        }
    }


    override fun onStart() {
        super.onStart()

        val currentUser = auth.currentUser
        if(currentUser != null){
       //     reload();
        }
    }










    private fun createAccount(email: String, password: String) {
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    Log.d(TAG, "createUserWithEmail:success")
                    val user = auth.currentUser
                    updateUI(user)

                } else {
                    // If sign in fails, display a message to the user.
                    Log.w(TAG, "createUserWithEmail:failure", task.exception)
                    Toast.makeText(baseContext, "Authentication failed.",
                        Toast.LENGTH_SHORT).show()
                  //  updateUI(null)
                }
            }


    }

    private fun updateUI(user: FirebaseUser?) {

        val intent = Intent(this, AfterLoginActivity::class.java)
        startActivity(intent)

    }

}