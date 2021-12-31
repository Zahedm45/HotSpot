package com.example.hotspot.model

import android.app.Activity

data class LoginInfo(
    val email: String,
    val password: String,
//    var isRememberMeChecked: Boolean,
    val activity: Activity
)