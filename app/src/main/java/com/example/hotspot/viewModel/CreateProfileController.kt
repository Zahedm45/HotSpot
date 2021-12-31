package com.example.hotspot.viewModel

import android.graphics.Bitmap
import android.widget.Toast
import com.example.hotspot.Repository.Repository

import com.example.hotspot.databinding.ActivityCreateProfileBinding
import com.example.hotspot.model.User
import com.example.hotspot.view.CreateProfileActivity

class CreateProfileController(
    private val createProfileActivity: CreateProfileActivity,
    private val binding: ActivityCreateProfileBinding
) {

    val rp2 = Repository



    fun createNewProfile(bitmap: Bitmap?, onSuccess: () -> Unit, onFail: (msg: String) -> Unit) {

        val user = verifyInput( bitmap) { msg -> onFail(msg) } ?: return
        rp2.createUserInFirebase( user, {onSuccess()}, { mgs -> onFail(mgs)})



    }






    private fun verifyInput(bitmap: Bitmap?, onFailure: (message: String) -> Unit): User? {

//        val userName = binding.activityCreateProfileUsername.text
        val name = binding.activityCreateProfileName.text
        val email = binding.activityCreateProfileEmailinput.text
        val age = binding.activityCreateProfileAge.text
        val women = binding.activityCreateProfileWomen
        val men = binding.activityCreateProfileMen
        val password = binding.activityCreateProfilePassword.text
        val repeatPassword = binding.activityCreateProfileRepeatPassword.text
        val bio = binding.activityCreateProfileBio.text

//        if (userName.isNullOrBlank()) {

//        }

        if (bitmap == null) {
            onFailure("Select image..! ")
            return null
        }

        if (name.isNullOrBlank()) {
            onFailure("Navn?")
            return null
        }

        if (password.isNullOrBlank()) {
            onFailure("Kodeord er tom!")
            return null
        }


        if (password.toString() != repeatPassword.toString()) {
            onFailure("Kodeord != GentageKodeord")
            return null
        }


        if (email.isNullOrBlank()) {
            onFailure("Email er tom! ")
            return null
        }

        if (age.isNullOrBlank()) {
            onFailure("Alder er tom!")
            return null
        }


        var gender: String? = null
        if (women.isChecked) {
            gender = women.text.toString()

        } else if (men.isChecked) {
            gender = men.text.toString()
        }

        if (gender == null) {
            onFailure("Køn?")
            return null
        }

        return User(
            name = name.toString(),
            age = age.toString().toInt(),
            emailAddress = email.toString(),
            bio = bio.toString(),
            gender = gender,
            password = password.toString(),
            bitmapImg = bitmap
        )


    }


}