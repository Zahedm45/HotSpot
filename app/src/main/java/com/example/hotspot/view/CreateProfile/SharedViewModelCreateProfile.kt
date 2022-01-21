package com.example.hotspot.view.CreateProfile

import android.graphics.Bitmap
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.hotspot.repository.Repository
import com.google.firebase.auth.FirebaseAuth

class SharedViewModelCreateProfile : ViewModel() {
    private val _firstName = MutableLiveData<String>()

    fun getFirstName() : LiveData<String> {
        return _firstName
    }

    fun setName(name : String){
        _firstName.value = name
    }

    private val _profileText = MutableLiveData<String>()
    private val _dateString = MutableLiveData<String>()
    private val _dayOfBirth = MutableLiveData<Int>()
    private val _monthOfBirth = MutableLiveData<Int>()
    private val _yearOfBirth = MutableLiveData<Int>()
    private val _gender = MutableLiveData<String>()
    private val _image = MutableLiveData<Bitmap>()
    private val _email = MutableLiveData<String>()

    //Getters BEGIN


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

    fun getImage() : LiveData<Bitmap> {
        return _image
    }

    fun getEmail() : LiveData<String>{
        return _email
    }
    // Getters END

    // Setters BEGIN



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

    fun setImageUri(bitMap : Bitmap){
        _image.value = bitMap
    }

    fun setEmail(email : String){
        _email.value = email
    }

    // Setters END


    //TODO previous code segment, should be deleted.
    /*
    fun createUserInDatabase() {
        auth = FirebaseAuth.getInstance()
        val uid = auth.currentUser?.uid
        databaseReference =  FirebaseDatabase.getInstance().getReference("users")

        val user = com.example.hotspot.model.User(
            this._firstName.value.toString(), 18, "randomEmail",
            this._profileText.value, this._gender.value.toString()
        )

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

     */

    val rp2 = Repository



    fun createNewProfile(onSuccess: () -> Unit, onFail: (msg: String) -> Unit) {

        val user = verifyInput { msg -> onFail(msg) } ?: return
        rp2.addProfileToFirebase( user, {onSuccess()}, { mgs -> onFail(mgs)})

    }






    private fun verifyInput( onFailure: (message: String) -> Unit): com.example.hotspot.model.User? {


        val name = this._firstName.value.toString()
        val email = this._email.value.toString()
        val age = this._dayOfBirth.value
        val gender = this._gender.value.toString()
        val bio = this._profileText.value.toString()
        val bitMap = this._image.value
        val day = this._dayOfBirth.value
        val month = this._monthOfBirth.value
        val year = this._yearOfBirth.value


        if (bitMap == null) {
            onFailure("It seems you forgot to upload an image.")
            return null
        }

        if (name.isNullOrBlank()) {
            onFailure("It seems you forgot to input a name")
            return null
        }


        if (email.isNullOrBlank()) {
            onFailure("It seems you forgot to enter an email.")
            return null
        }

        if (age == null) {
            onFailure("It seems you forgot to enter you date of birth.")
            return null
        }

        if (gender.isNullOrBlank()) {
            onFailure("You forgot to enter gender.")
            return null
        }

        if(day == null || month == null || year == null){
            onFailure("Wrong date.")
            return null
        }

        return com.example.hotspot.model.User(
            uid = FirebaseAuth.getInstance().uid,
            name = name,
            age = age,
            emailAddress = email,
            bio = bio,
            gender = gender,
            bitmapImg = bitMap,
            day = day,
            month = month,
            year = year
        )
    }
}