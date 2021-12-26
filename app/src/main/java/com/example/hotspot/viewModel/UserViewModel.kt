package com.example.hotspot.viewModel
import android.content.ContentValues.TAG
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.hotspot.model.User

class UserViewModel : ViewModel() {

    private var user = User("First name", 24, "zahedm@gmail.com", "no userName", "noPass")

    private var mutableUser = MutableLiveData(user)
    fun getUserData(): LiveData<User> = mutableUser


    fun getUpdate() {



        Log.i(TAG ,"getUpdate...")
//        use.value?.name  = "Maya"
//        use.value?.age  = 25
//        use.value?.emailAddress  = "zahedm45@gmai.com"
//        User("Maya", 25, "....")

        var newUser = user
        newUser.age = 4
        mutableUser.value = newUser

    }






}