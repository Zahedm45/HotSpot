package com.example.hotspot.viewModel

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import com.example.hotspot.repository.Repository
import com.example.hotspot.databinding.FragmentPersonalProfileBinding
import com.example.hotspot.model.UserProfile
import com.example.hotspot.other.UtilCalculations
import com.google.firebase.firestore.DocumentSnapshot

class PersonalProfileVM(
    ) {


    companion object {

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

            repository.getUserProfile { snapshot -> updateProfileUI(snapshot) }
        }





        private fun updateProfileUI(snapshot: DocumentSnapshot) {


            val name = snapshot.get("name").toString()
            val email = snapshot.get("emailAddress").toString()
            val gender = snapshot.get("gender").toString()
            val userName = snapshot.get("userName").toString()
            val bio = snapshot.get("bio").toString()
            val password = snapshot.get("password").toString()
            val dayOfBirth = snapshot.get("day")
            val monthOfBirth = snapshot.get("month")
            val yearOfBirth = snapshot.get("year")
            val age = UtilCalculations.getAge(yearOfBirth, monthOfBirth, dayOfBirth)




            userProfile = UserProfile(name = name, age = age, password = password,
                emailAddress = email, gender = gender, userName = userName, bio = bio)

            binding.fragmentPersonalProfilePersonName.text = userProfile.name
            binding.fragmentPersonalProfileAge.text = "${userProfile.age}"
            binding.fragmentPersonalProfileBio.text = userProfile.bio
            binding.fragmentPersonalProfileEmailTv.text = "${userProfile.emailAddress}"


            if (userProfile.bitmapImg != null) {
                binding.fragmentPersonalProfilePicture.setImageBitmap(userProfile.bitmapImg)
            }



            //     binding.fragmentPersonalProfilePicture.setImageURI(null)

//            val storageRef = FirebaseStorage.getInstance().reference.child("images/${DataHolder.fbUser!!.uid}")
//            val localFile = File.createTempFile("temImage", "jpeg")
//
//            storageRef.getFile(localFile).addOnSuccessListener {
//
//                val bit = BitmapFactory.decodeFile(localFile.absolutePath)
//
//                binding.fragmentPersonalProfilePicture.setImageBitmap(bit)
//
//            }

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