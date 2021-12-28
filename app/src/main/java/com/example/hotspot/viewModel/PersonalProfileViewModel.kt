package com.example.hotspot.viewModel
import android.content.ContentValues.TAG
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.hotspot.Repository.Repository
import com.example.hotspot.model.User
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.FirebaseFirestore

class PersonalProfileViewModel(): ViewModel() {


    fun getUserData(): LiveData<User> = mutableUser

    companion object {
        lateinit var mutableUser : MutableLiveData<User>

    }
















}