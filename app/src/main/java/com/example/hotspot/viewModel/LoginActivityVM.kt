package com.example.hotspot.viewModel

import android.widget.Toast
import com.example.hotspot.Repository.Repository
import com.example.hotspot.databinding.ActivityLoginBinding
import com.example.hotspot.view.LoginActivity

class LoginActivityVM(
    private val activity: LoginActivity,
    private val binding: ActivityLoginBinding
    ){

    private val repository = Repository

    fun login(
        onSuccess: (() -> Unit),
        onFail: ((msg: String) -> Unit)) {

        val email = binding.activityLoginEmail.text?.toString()
        val password = binding.activityLoginPassword.text?.toString()

        if (email.isNullOrBlank()) {
            onFail("Email? ")
            return
        }

        if (password.isNullOrBlank()) {
            onFail("Password?")
            return
        }

         repository.login(activity, email, password, {onSuccess()}, {msg -> onFail(msg)} )

    }


}