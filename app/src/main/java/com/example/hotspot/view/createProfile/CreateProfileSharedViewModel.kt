package com.example.hotspot.view.createProfile

import android.net.Uri
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class CreateProfileSharedViewModel : ViewModel() {
    private val _firstName = MutableLiveData<String>()
    private val _profileText = MutableLiveData<String>()
    private val _dateString = MutableLiveData<String>()
    private val _dayOfBirth = MutableLiveData<Int>()
    private val _monthOfBirth = MutableLiveData<Int>()
    private val _yearOfBirth = MutableLiveData<Int>()
    private val _gender = MutableLiveData<String>()
    private val _image = MutableLiveData<Uri?>()

    //Getters BEGIN
    fun getFirstName() : LiveData<String> {
        return _firstName
    }

    fun getProfileText() : LiveData<String>{
        return _profileText
    }

    fun getDateString() : LiveData<String>{
        return _dateString
    }
    fun getDayOfBirth() : LiveData<Int> {
        return _dayOfBirth
    }

    fun getMonth() : LiveData<Int> {
        return _monthOfBirth
    }

    fun getYear() : LiveData<Int> {
        return _yearOfBirth
    }

    fun getGender() : LiveData<String> {
        return _gender
    }

    fun getImage() : LiveData<Uri?> {
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

    fun setdateString(dateString : String){
        _dateString.value = dateString
    }

    fun setDayOfBirth(day : Int){
        _dayOfBirth.value = day
    }

    fun setMonth(age : Int){
        _monthOfBirth.value = age
    }

    fun setYear(age : Int){
        _yearOfBirth.value = age
    }

    fun setGender(gender : String){
        _gender.value = gender
    }

    fun setImageUri(uriCode : Uri?){
        _image.value = uriCode
    }

    // Setters END
}