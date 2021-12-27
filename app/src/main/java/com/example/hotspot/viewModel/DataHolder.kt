package com.example.hotspot.viewModel

import com.example.hotspot.Repository.Repository
import com.google.firebase.auth.FirebaseUser


class DataHolder {

 //   private lateinit var user: User


    companion object {
        val repository = Repository()
        lateinit var fbUser : FirebaseUser

    }
}