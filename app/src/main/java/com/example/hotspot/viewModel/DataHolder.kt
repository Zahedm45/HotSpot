package com.example.hotspot.viewModel

import android.app.Activity
import android.content.Context
import android.content.SharedPreferences
import com.example.hotspot.model.LoginInfo
import com.example.hotspot.model.User
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.ktx.Firebase


class DataHolder {



    companion object {
      //  var getCheckedInUsers: ArrayList<User> = ArrayList()

        fun getUserFromDB(usersId: String, onSuccess: ((user: User) -> Unit)) {
            val db = Firebase.firestore
            val tempUsers = ArrayList<User>()


            db.collection("users").document(usersId)
                .get()
                .addOnSuccessListener { doc ->
                    doc.toObject<User>()?.apply {
                        onSuccess(this)
                    }


                }

        }




    }









}