package com.example.hotspot.viewModel

import android.content.ContentValues.TAG
import android.graphics.Bitmap
import android.util.Log
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import com.example.hotspot.R
import com.example.hotspot.repository.Repository
import com.example.hotspot.databinding.FragmentEditProfileBinding
import com.example.hotspot.model.UserProfile
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch

class EditProfileVM {

    companion object {

        private val repository = Repository
        private lateinit var binding: FragmentEditProfileBinding
        private lateinit var userProfile: UserProfile
        private var userPic: Bitmap? = PersonalProfileVM.userPic
        private lateinit var fragment: Fragment
        private var counter1 = 0
        private var counter2 = 0




        fun getUserProfile(binding: FragmentEditProfileBinding, fragment: Fragment) {
            this.fragment = fragment
            this.binding = binding
            userProfile = PersonalProfileVM.userProfile



            binding.editProfileNewEmail.setText(userProfile.emailAddress)
            binding.editProfileBioText.setText(userProfile.bio)

            if (userPic != null) {
                binding.fragmentEditProfileEditPicture.setImageBitmap(userPic)
            }

        }





        fun updateUserProfile(bitMap: Bitmap?) {
            counter2 = 0
            counter1 = 0

            if (bitMap != null) {
                counter1++
                repository.updateUserPicInDB(bitMap, { increaseCounter() }, null)

            }


            val email = binding.editProfileNewEmail.text.toString()
            val newBio = binding.editProfileBioText.text.toString()

            val strArr = ArrayList<String>()

            if (email != userProfile.emailAddress){
                // not doing anything for now...
            }

            if (newBio != userProfile.bio) {
                strArr.add("bio")
                strArr.add(newBio)
            }

            if (strArr.size > 0) {
                counter1++
                repository.updateUserFieldInDB("users", strArr, { increaseCounter()}, null)

            }



        }




        private fun navigateToPersonalProfile() {
            Toast.makeText(fragment.context, "Successfully saved.", Toast.LENGTH_SHORT).show()

            //fragment.view?.let { Navigation.findNavController(it).navigate(R.id.action_editProfile_to_personalProfile) }


        }



        private fun increaseCounter() {
            Log.i(TAG, "increased is called..")
            counter2++

            if (counter1 == counter2) {
                Log.i(TAG, "navigate is called..")

            }
        }

        fun deleteUser(){
            CoroutineScope(IO).launch {
                repository.deleteUser()
            }
        }
    }



}