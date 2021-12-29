package com.example.hotspot.viewModel

import android.app.ProgressDialog
import android.content.Context
import com.example.hotspot.Repository.Repository
import com.example.hotspot.model.UserProfile
import com.google.firebase.auth.FirebaseUser


class DataHolder {

 //   private lateinit var user: User


    companion object {
        val repository = Repository()
        var fbUser : FirebaseUser? = null
//        var userProfile: UserProfile? = null


        fun showProgress(context: Context): ProgressDialog {
            val pd = ProgressDialog(context)
            pd.setTitle("Please wait")
            pd.setMessage("Loading ...")
            return pd
        }

    }
}