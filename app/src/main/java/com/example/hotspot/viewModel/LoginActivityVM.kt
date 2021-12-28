package com.example.hotspot.viewModel

import android.widget.Toast
import com.example.hotspot.Repository.Repository
import com.example.hotspot.databinding.ActivityLoginBinding
import com.example.hotspot.view.LoginActivity
import com.google.firebase.auth.FirebaseAuth

class LoginActivityVM(
    private val activity: LoginActivity,
    private val binding: ActivityLoginBinding,
    private val repository: Repository

    ){



    fun login(auth: FirebaseAuth) {

        val email = binding.activityLoginEmail.text?.toString()
        val password = binding.activityLoginPassword.text?.toString()

        if (email.isNullOrBlank()) {
            Toast.makeText(activity.baseContext ,"Email..! ", Toast.LENGTH_SHORT).show()
            return
        }

        if (password.isNullOrBlank()) {
            Toast.makeText(activity.baseContext ,"Password..! ", Toast.LENGTH_SHORT).show()
            return
        }

        repository.login(activity, binding, auth)

    }


}