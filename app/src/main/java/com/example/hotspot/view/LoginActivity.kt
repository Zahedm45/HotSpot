package com.example.hotspot.view

import android.app.ProgressDialog
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.InputType
import android.util.Log
import android.widget.EditText
import android.widget.Toast
import com.example.hotspot.databinding.ActivityLoginSuggestionBinding
import com.example.hotspot.repository.Repository
import com.example.hotspot.view.createProfilePackage.ActivityCreateProfile
import com.example.hotspot.view.phoneAuthentification.ActivityPhoneAuthentification
import com.example.hotspot.viewModel.LoginActivityVM
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.core.Repo
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking


class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginSuggestionBinding
    private lateinit var auth: FirebaseAuth
    private lateinit var loginActivityMV: LoginActivityVM
    private var progressDialog: ProgressDialog? = null
    private val repository = Repository
    private var isDocumentsCreated = false


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
       // setContentView(R.layout.activity_login)

        binding = ActivityLoginSuggestionBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.hide()

        auth = Firebase.auth

        val user = Firebase.auth.currentUser

        //hello()
        loginActivityMV = LoginActivityVM(this)

        loadLoginInfo()

        binding.activityLoginCreateProfileBtn.setOnClickListener {
            val intentPhoneAuth = Intent(this, ActivityPhoneAuthentification::class.java)
            startActivity(intentPhoneAuth)
        }



        progressDialog = ProgressDialog(this)
        progressDialog?.setTitle("Please wait")
        progressDialog?.setMessage("Loading ...")


        binding.activityLoginLoginBtn.setOnClickListener {
            Toast.makeText(this,"To be implemented", Toast.LENGTH_SHORT).show()
            //progressDialog!!.show()
            //loginActivityMV.login( {updateUIOnSuccess()}, { msg -> updateUIOnFail(msg)} )


        }

        binding.activityLoginForgotPassword.setOnClickListener {
            //TODO delete forgot password function, no longer necessary
           // forgotPassword()
        }


    }

    private fun hello() {
        val user = Firebase.auth.currentUser
        if (user != null) {
            CoroutineScope(IO).launch {
                val isCreated = repository.isUserProfileCreated()
                Log.d("LUCASLOFT", isCreated.toString())
            }

            /*
            val intent = Intent(this, ActivityCreateProfile::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
            startActivity(intent)
            finish()

             */
            // User is signed in.
            //auth.signOut() //TODO for now to troubleshoot sign up activity.
        } else {

        }
    }





    private fun saveLoginInfo() {

        val sharedP = getSharedPreferences("userLogin", Context.MODE_PRIVATE)
        val editor = sharedP.edit()
        val email = "email"
        val password = "pw"
        val isSaveChecked = true

        editor.apply {
            putString("STRING_EMAIL", email)
            putString("STRING_PASSWORD", password)
            putBoolean("BOOLEAN_IS_SAVE_CHECKED", isSaveChecked)

        }.apply()

    }

    private fun isUserCreated(snapshot: DocumentSnapshot){
        if(snapshot.exists()) {
            auth = Firebase.auth

            isDocumentsCreated = true
            Toast.makeText(this,"auth.currentUser?.phoneNumber", Toast.LENGTH_SHORT).show()
        }
        else{
            Toast.makeText(this,"no Snapshot", Toast.LENGTH_SHORT).show()
        }
    }


    private fun loadLoginInfo() {

        val sharedP = getSharedPreferences("userLogin", Context.MODE_PRIVATE)
        val isSaveChecked = sharedP.getBoolean("BOOLEAN_IS_SAVE_CHECKED", false)

        if (!isSaveChecked) {
            return
        }


        val email = sharedP.getString("STRING_EMAIL", null)
        val password = sharedP.getString("STRING_PASSWORD", null)




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



