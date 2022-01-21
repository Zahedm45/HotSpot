package com.example.hotspot.viewModel

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Log
import com.example.hotspot.repository.Repository
import com.example.hotspot.databinding.FragmentPersonalProfileBinding
import com.example.hotspot.model.UserProfile
import com.example.hotspot.other.util.UtilCalculations
import com.example.hotspot.other.network.TAG
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.ListenerRegistration

class PersonalProfileVM(
    ) {


    companion object {


        var getUserProfileReg: ListenerRegistration? = null
        private lateinit var binding: FragmentPersonalProfileBinding
        private val repository = Repository
        var userPic: Bitmap? = null
        lateinit var userProfile: UserProfile


        fun getProfile(binding: FragmentPersonalProfileBinding) {
            this.binding = binding

            if (userPic == null) {
                repository.getUserPicFromDB { bytes -> setUserPicUI(bytes) }

            } else {
                binding.fragmentPersonalProfilePicture.setImageBitmap(userPic)
            }

            getUserProfileReg = repository.getUserProfile { snapshot -> updateProfileUI(snapshot) }
        }





         fun updateProfileUI(snapshot: DocumentSnapshot) {

            var dayOfBirth = 14
            var monthOfBirth = 11
            var yearOfBirth = 2000
            val name = snapshot.get("name").toString()
            val email = snapshot.get("emailAddress").toString()
            val gender = snapshot.get("gender").toString()
            val userName = snapshot.get("userName").toString()
            val bio = snapshot.get("bio").toString()
            val password = snapshot.get("password").toString()

            if(snapshot.get("month") != null) {
                dayOfBirth = snapshot.get("day").toString().toInt()
                monthOfBirth = snapshot.get("month").toString().toInt()
                yearOfBirth = snapshot.get("year").toString().toInt()
            }
            var age = UtilCalculations.getAge(yearOfBirth, monthOfBirth, dayOfBirth)


            if (age == 0) {
                age = snapshot.get("age").toString().toInt()
            }
             Log.i(TAG, "Age...$age")



            userProfile = UserProfile(name = name, age = age, password = password,
                emailAddress = email, gender = gender, userName = userName, bio = bio, day = dayOfBirth, month = monthOfBirth, year = yearOfBirth)
             var showName = StringBuilder()
             showName.append(userProfile.name)
             showName.append(",")

            binding.fragmentPersonalProfilePersonName.text = showName
            binding.fragmentPersonalProfileAge.text = "${userProfile.age}"
            binding.fragmentPersonalProfileBio.text = userProfile.bio
            binding.fragmentPersonalProfileEmailTv.text = "${userProfile.emailAddress}"

             binding.tvPhoneNumbber.text = repository.getPhoneNumber()


            if (userProfile.bitmapImg != null) {
                binding.fragmentPersonalProfilePicture.setImageBitmap(userProfile.bitmapImg)
            }

        }




        fun setUserPicUI(bytes: ByteArray?) {

            if (bytes == null) {
                return
            }
            userPic = BitmapFactory.decodeByteArray(bytes, 0, bytes.size)
            binding.fragmentPersonalProfilePicture.setImageBitmap(userPic)

        }



    }











}