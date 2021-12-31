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
//            Toast.makeText(
//                createProfileActivity.baseContext,
//                "User name er tom! ",
//                Toast.LENGTH_SHORT
//            ).show()
//            return null
//        }

        if (bitmap == null) {
            onFailure("Select image..! ")
//            Toast.makeText(
//                createProfileActivity.baseContext,
//                "Select image..! ",
//                Toast.LENGTH_SHORT
//            ).show()
            return null
        }

        if (name.isNullOrBlank()) {
            onFailure("Navn?")
//            Toast.makeText(createProfileActivity.baseContext, "Navn ", Toast.LENGTH_SHORT).show()
            return null
        }

        if (password.isNullOrBlank()) {
            onFailure("Kodeord er tom!")
//            Toast.makeText(
//                createProfileActivity.baseContext,
//                "Kodeord er tom! ",
//                Toast.LENGTH_SHORT
//            ).show()
            return null
        }


        if (password.toString() != repeatPassword.toString()) {
            onFailure("Kodeord != GentageKodeord")
//            Toast.makeText(
//                createProfileActivity.baseContext,
//                "Kodeord != GentageKodeord",
//                Toast.LENGTH_SHORT
//            ).show()
            return null
        }


        if (email.isNullOrBlank()) {
            onFailure("Email er tom! ")
//            Toast.makeText(createProfileActivity.baseContext, "Email er tom! ", Toast.LENGTH_SHORT)
//                .show()
            return null
        }

        if (age.isNullOrBlank()) {
            onFailure("Alder er tom!")
//            Toast.makeText(createProfileActivity.baseContext, "Alder er tom!", Toast.LENGTH_SHORT)
//                .show()
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
//            Toast.makeText(createProfileActivity.baseContext, "Køn?", Toast.LENGTH_SHORT).show()
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