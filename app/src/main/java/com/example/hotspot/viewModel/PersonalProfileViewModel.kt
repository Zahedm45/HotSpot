package com.example.hotspot.viewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.hotspot.model.UserProfile

class PersonalProfileViewModel(): ViewModel() {


    fun getUserData(): LiveData<UserProfile> = mutableUserProfile

    companion object {
        lateinit var mutableUserProfile : MutableLiveData<UserProfile>

    }
















}