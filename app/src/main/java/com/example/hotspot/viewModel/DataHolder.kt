package com.example.hotspot.viewModel

import android.annotation.SuppressLint
import android.app.ProgressDialog
import android.content.Context
import com.example.hotspot.Repository.Repository
import com.example.hotspot.model.User
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.FirebaseFirestore


class DataHolder {

 //   private lateinit var user: User


    companion object {
        val repository = Repository()
        var fbUser : FirebaseUser? = null
        var user: User? = null


        fun showProgress(context: Context) {
            val pd = ProgressDialog(context)
            pd.setTitle("Please wait")
            pd.setMessage("Loading ...")
            pd.show()
        }

    }
}