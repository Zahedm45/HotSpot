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





    private lateinit var user : User

    private lateinit var mutableUser : MutableLiveData<User>

    init {
        if (DataHolder.user != null) {
            user = DataHolder.user!!
            mutableUser = MutableLiveData(user)
        }
    }

    fun getUserData(): LiveData<User> {

        return mutableUser

    }











    fun getUpdate() {



    }






}