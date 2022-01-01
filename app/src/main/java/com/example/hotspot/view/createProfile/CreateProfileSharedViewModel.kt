package com.example.hotspot.view.createProfile

import android.net.Uri
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class CreateProfileSharedViewModel : ViewModel() {
    private val _firstName = MutableLiveData<String>()
    private val _profileText = MutableLiveData<String>()
    private val _age = MutableLiveData<Int>()
    private val _gender = MutableLiveData<String>()
    private val _image = MutableLiveData<Uri>()

    //Getters BEGIN
    fun getFirstName() : LiveData<String> {
        return _firstName
    }

    fun getProfileText() : LiveData<String>{
        return _profileText
    }

    fun getAge() : LiveData<Int> {
        return _age
    }

    fun getGender() : LiveData<String> {
        return _gender
    }

    fun getImage() : LiveData<Uri> {
        return _image
    }
    // Getters END

    // Setters BEGIN

    fun setName(name : String){
        _firstName.value = name
    }

    fun setProfileText(profileText : String){
        _profileText.value = profileText
    }

    fun setAge(age : Int){
        _age.value = age
    }

    fun setGender(gender : String){
        _gender.value = gender
    }

    fun setImageUri(uriCode : Uri){
        _image.value = uriCode
    }

    // Setters END
}