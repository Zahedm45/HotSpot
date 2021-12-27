package com.example.hotspot.viewModel

import com.example.hotspot.Repository.Repository
import com.example.hotspot.databinding.ActivityLoginBinding
import com.example.hotspot.view.MainActivity
import com.google.firebase.auth.FirebaseAuth

class MainActivityVM(private val activity: MainActivity,
                     private val binding: ActivityLoginBinding,
                     private val repository: Repository) {



    fun login(auth: FirebaseAuth) {

        val email = binding.activityLoginEmail.text?.toString()
        val password = binding.activityLoginPassword.text?.toString()

        if (!email.isNullOrBlank() && !password.isNullOrEmpty()) {
            repository.login(activity, binding, auth)
        }



    }
}