package com.example.hotspot.viewModel

import android.content.ContentValues.TAG
import android.graphics.Bitmap
import android.util.Log
import com.example.hotspot.Repository.Repository
import com.example.hotspot.databinding.FragmentEditProfileBinding

class EditProfileVM {

    companion object {

        private val repository = Repository
        private lateinit var binding: FragmentEditProfileBinding
        private val userProfile = PersonalProfileVM.userProfile
        private var userPic: Bitmap? = PersonalProfileVM.userPic



        fun getUserProfile(binding: FragmentEditProfileBinding) {

            this.binding = binding
            binding.editProfileNewName.setText(userProfile.name)
            binding.editProfileNewEmail.setText(userProfile.emailAddress)
            binding.editProfileBioText.setText(userProfile.bio)

            if (userPic != null) {
                binding.fragmentEditProfileEditPicture.setImageBitmap(userPic)
            }

        }



        fun updateUserProfile(bitMap: Bitmap?) {

            val password = binding.editProfilePassword.text.toString()
            if (password != userProfile.password) {
                return
            }

            if (bitMap != null) {
                repository.updateUserPicInDB(bitMap, null, null)


            }


            val newName = binding.editProfileNewName.text.toString()
            val email = binding.editProfileNewEmail.text.toString()
            val newBio = binding.editProfileBioText.text.toString()

            val strArr = ArrayList<String>()


            if (newName != userProfile.name) {
                strArr.add("name")
                strArr.add(newName)
            }

            if (email != userProfile.emailAddress){
                // not doing anything for now...
            }

            if (newBio != userProfile.bio) {
                strArr.add("bio")
                strArr.add(newBio)
            }

            repository.getUpdate("users", strArr)


        }

    }



}