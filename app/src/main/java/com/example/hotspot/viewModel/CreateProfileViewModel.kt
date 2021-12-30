package com.example.hotspot.viewModel

import android.net.Uri
import android.widget.Toast
import androidx.lifecycle.ViewModel
import com.example.hotspot.Repository.Repository
import com.example.hotspot.databinding.ActivityCreateProfileBinding
import com.example.hotspot.view.CreateProfileActivity
import com.google.firebase.auth.FirebaseAuth

class CreateProfileViewModel(
    private val repository: Repository,
    val createProfileActivity: CreateProfileActivity,
    val binding: ActivityCreateProfileBinding,


) : ViewModel() {


    fun createNewProfile(auth: FirebaseAuth, uri: Uri?) {

        if (!verifyInput(uri)) {
            return
        }

        repository.createUserInFirebase(createProfileActivity, binding, auth, uri)
    }



    fun verifyInput(uri: Uri?): Boolean {

        val userName = binding.activityCreateProfileUsername.text.isNullOrBlank()
        val name = binding.activityCreateProfileName.text.isNullOrBlank()
        val email = binding.activityCreateProfileEmailinput.text.isNullOrBlank()
        val age = binding.activityCreateProfileAge.text.isNullOrBlank()
        val women = binding.activityCreateProfileWomen
        val men = binding.activityCreateProfileMen
        val password = binding.activityCreateProfilePassword.text
        val repeatPassword = binding.activityCreateProfileRepeatPassword.text

        if (userName) {
            Toast.makeText(createProfileActivity.baseContext ,"User name er tom! ", Toast.LENGTH_SHORT).show()
            return false
        }

        if (uri == null) {
            Toast.makeText(createProfileActivity.baseContext ,"Select image..! ", Toast.LENGTH_SHORT).show()
            return false
        }

        if (name) {
            Toast.makeText(createProfileActivity.baseContext ,"Navn ", Toast.LENGTH_SHORT).show()
            return false
        }

        if (password.isNullOrBlank()) {
            Toast.makeText(createProfileActivity.baseContext ,"Kodeord er tom! ", Toast.LENGTH_SHORT).show()
            return false
        }


        if (password.toString() != repeatPassword.toString()) {
            Toast.makeText(createProfileActivity.baseContext ,"Kodeord != GentageKodeord", Toast.LENGTH_SHORT).show()
            return false
        }


        if (email) {
            Toast.makeText(createProfileActivity.baseContext ,"Email er tom! ", Toast.LENGTH_SHORT).show()
            return false
        }

        if (age) {
            Toast.makeText(createProfileActivity.baseContext ,"Alder er tom! ", Toast.LENGTH_SHORT).show()
            return false
        }



        var gender: String? = null
        if (women.isChecked) {
            gender = women.text.toString()

        } else if (men.isChecked) {
            gender = men.text.toString()
        }

        if (gender == null) {
            Toast.makeText(createProfileActivity.baseContext ,"Køn? ", Toast.LENGTH_SHORT).show()
            return false
        }

        return true

    }


}