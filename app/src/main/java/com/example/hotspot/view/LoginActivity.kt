package com.example.hotspot.view

import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.InputType
import android.widget.EditText
import android.widget.Toast
import com.example.hotspot.databinding.ActivityLoginBinding
import com.example.hotspot.viewModel.DataHolder
import com.example.hotspot.viewModel.LoginActivityVM
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase


class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding
    private lateinit var auth: FirebaseAuth
    private lateinit var loginActivityMV: LoginActivityVM
    private val repository = DataHolder.repository







    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
       // setContentView(R.layout.activity_login)

        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.hide()

        auth = Firebase.auth

        loginActivityMV = LoginActivityVM(this, binding, repository)

        loadLoginInfo()

        binding.activityLoginCreateProfileBtn.setOnClickListener {
            val intent = Intent(this, CreateProfileActivity::class.java)
            startActivity(intent)
        }


        binding.activityLoginLoginBtn.setOnClickListener {

            loginActivityMV.login(auth)

            if (binding.activityLoginRememberMe.isChecked){
                saveLoginInfo()

            } else {

                val sharedP = getSharedPreferences("userLogin", Context.MODE_PRIVATE)
                sharedP.edit().clear().apply()
            }
        }

        binding.activityLoginForgotPassword.setOnClickListener {
            forgotPassword()
        }


    }





    private fun saveLoginInfo() {

        val sharedP = getSharedPreferences("userLogin", Context.MODE_PRIVATE)
        val editor = sharedP.edit()
        val email = binding.activityLoginEmail.text.toString()
        val password = binding.activityLoginPassword.text.toString()
        val isSaveChecked = binding.activityLoginRememberMe.isChecked

        editor.apply {
            putString("STRING_EMAIL", email)
            putString("STRING_PASSWORD", password)
            putBoolean("BOOLEAN_IS_SAVE_CHECKED", isSaveChecked)

        }.apply()

    }


    private fun loadLoginInfo() {

        val sharedP = getSharedPreferences("userLogin", Context.MODE_PRIVATE)
        val isSaveChecked = sharedP.getBoolean("BOOLEAN_IS_SAVE_CHECKED", false)

        if (!isSaveChecked) {
            return
        }


        val email = sharedP.getString("STRING_EMAIL", null)
        val password = sharedP.getString("STRING_PASSWORD", null)


        binding.activityLoginEmail.setText(email)
        binding.activityLoginPassword.setText(password)
        binding.activityLoginRememberMe.isChecked = isSaveChecked

    }




    private fun forgotPassword() {


        val builder = android.app.AlertDialog.Builder(this)
        builder.setTitle("Reset your password")

        val input = EditText(this)
        input.hint = "Enter email"
        input.inputType = InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS

        builder.setView(input)

        builder.setPositiveButton("OK", DialogInterface.OnClickListener { dialog, it ->
            val email = input.text.toString()

            Firebase.auth.sendPasswordResetEmail(email)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        Toast.makeText(this.baseContext, "A mail sent to $email", Toast.LENGTH_SHORT).show()

                    } else {
                        Toast.makeText(this.baseContext, "$email is not found", Toast.LENGTH_SHORT).show()
                    }
                }

        })

        builder.setNegativeButton("Cancel", DialogInterface.OnClickListener { dialog, it ->

        })

        builder.show()



    }



}



