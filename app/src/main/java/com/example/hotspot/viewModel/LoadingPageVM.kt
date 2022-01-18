package com.example.hotspot.viewModel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.hotspot.repository.Repository
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.auth.User
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.lang.Exception

class LoadingPageVM : ViewModel() {

    private val repository = Repository

    private val _user = MutableLiveData<FirebaseUser>()
    private val _isUserLoggedIn = MutableLiveData<Boolean>()

    fun getUser() : LiveData<FirebaseUser>{
        return _user
    }

    fun getIsUserLoggedIn() : LiveData<Boolean>{
        return _isUserLoggedIn
    }

    fun checkForCurrentuser(onSuccess: (() -> Unit),
                            onFailure: (() -> Unit)){

        _user.value = repository.getFirebaseUser()

        if (_user.value != null) {
            CoroutineScope(Dispatchers.IO).launch {
                try {
                    val isCreated = repository.isUserProfileCreated()
                    if(isCreated) {
                        onSuccess() // If profile has been created, we go to our Maps landing page.
                    }
                    else{
                        onFailure() //If no profile has been created, but user has been authed.
                    }
                }catch (e: Exception){
                    onFailure
                }

            }.isCompleted
        }
        else{
            CoroutineScope(Dispatchers.IO).launch{
                delay(1500)
                onFailure()} // in case of no user remember by our system, we just send them to login
        }
    }
}