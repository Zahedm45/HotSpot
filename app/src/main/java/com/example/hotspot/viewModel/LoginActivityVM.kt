package com.example.hotspot.viewModel

import com.example.hotspot.repository.Repository
import com.example.hotspot.databinding.ActivityLoginBinding
import com.example.hotspot.view.LoginActivity

class LoginActivityVM(
    private val activity: LoginActivity,

    ){

    private val repository = Repository

    fun login(
        onSuccess: (() -> Unit),
        onFail: ((msg: String) -> Unit)) {

        val email = "email"
        val password = "pw"

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