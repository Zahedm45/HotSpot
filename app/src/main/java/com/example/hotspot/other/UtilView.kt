package com.example.hotspot.other

import android.app.Activity
import android.content.Intent
import android.view.MenuItem
import androidx.core.content.ContextCompat.startActivity
import com.example.hotspot.R
import com.example.hotspot.view.LoginActivity
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

object UtilView {



     fun navigateToLoginPage(activity: Activity) {
        val intent = Intent(activity, LoginActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
        activity.startActivity(intent)
    }



    fun menuOptionClick(item: MenuItem, activity: Activity) {
        when (item.itemId) {
            R.id.menu_sign_out -> {
                Firebase.auth.signOut()
                navigateToLoginPage(activity)
            }
            // more to be added..
        }
    }
}