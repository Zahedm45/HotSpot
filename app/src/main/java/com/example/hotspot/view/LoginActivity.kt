package com.example.hotspot.view

import android.app.ProgressDialog
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.InputType
import android.widget.EditText
import android.widget.Toast
import com.example.hotspot.Repository.Repository
import com.example.hotspot.databinding.ActivityLoginBinding
import com.example.hotspot.model.LoginInfo
import com.example.hotspot.viewModel.DataHolder
import com.example.hotspot.viewModel.LoginActivityVM
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase


class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding
    private lateinit var loginActivityMV: LoginActivityVM
    private var progressDialog: ProgressDialog? = null







    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()

        loginActivityMV = LoginActivityVM(this, binding)

        loadLoginInfo()

        binding.activityLoginCreateProfileBtn.setOnClickListener {
            val intent = Intent(this, CreateProfileActivity::class.java)
            startActivity(intent)
        }



        progressDialog = ProgressDialog(this)
        progressDialog?.setTitle("Please wait")
        progressDialog?.setMessage("Loading ...")


        binding.activityLoginLoginBtn.setOnClickListener {
            progressDialog!!.show()
            loginActivityMV.login( {updateUIOnSuccess()}, { msg -> updateUIOnFail(msg)} )

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







    private fun updateUIOnSuccess() {

        progressDialog?.dismiss()
        Toast.makeText(baseContext, "Successfully login.", Toast.LENGTH_SHORT).show()
        val intent = Intent(this, AfterLoginActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
        startActivity(intent)
        finish()

    }


    private fun updateUIOnFail(msg: String) {
        progressDialog?.dismiss()
        Toast.makeText(baseContext, msg, Toast.LENGTH_LONG).show()
    }



}



