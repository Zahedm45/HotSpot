package com.example.hotspot.viewModel

import android.content.ContentValues.TAG
import android.graphics.Bitmap
import android.text.Editable
import android.util.Log
import com.example.hotspot.Repository.Repository
import com.example.hotspot.databinding.FragmentEditProfileBinding
import com.example.hotspot.databinding.FragmentPersonalProfileBinding

class EditProfileVM {

    companion object {

        private val repository = Repository
        private lateinit var binding: FragmentEditProfileBinding
        val userProfile = PersonalProfileVM.userProfile
        var userPic: Bitmap? = PersonalProfileVM.userPic



        fun getUserProfile(binding: FragmentEditProfileBinding) {

            this.binding = binding
            binding.editProfileNewName.setText(userProfile.name)
            binding.editProfileNewEmail.setText(userProfile.emailAddress)
            binding.editProfileBioText.setText(userProfile.bio)

            if (userPic != null) {
                binding.fragmentEditProfileEditPicture.setImageBitmap(userPic)
            }

        }



        fun updateUserProfile() {

            val password = binding.editProfilePassword.toString()
            Log.i(TAG, "here is password ${userProfile.password.toString()}")

            if (password != userProfile.password) {
                return
            }






        }

    }



}