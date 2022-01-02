package com.example.hotspot.view.createProfilePackage

import android.net.Uri
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.firestore.auth.User
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference

class SharedViewModelCreateProfile : ViewModel() {
    private lateinit var auth : FirebaseAuth
    private lateinit var databaseReference : DatabaseReference
    private lateinit var storageReference : StorageReference

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

    fun createUserInDatabase() {
        auth = FirebaseAuth.getInstance()
        val uid = auth.currentUser?.uid
        databaseReference =  FirebaseDatabase.getInstance().getReference("users")

        val user = com.example.hotspot.model.User(
            this._firstName.value.toString(), 18, "randomEmail",
            "randomPassword", this._profileText.value, this._gender.value.toString())

        if(uid != null){
            databaseReference.child(uid).setValue(user).addOnCompleteListener{
                if(it.isSuccessful){
                    uploadImage()
                }

            }
        }
    }

    private fun uploadImage(){
        storageReference = FirebaseStorage.getInstance().getReference("Users/"+auth.currentUser?.uid)
        storageReference.putFile(this._image.value!!).addOnSuccessListener {

        }
    }
}