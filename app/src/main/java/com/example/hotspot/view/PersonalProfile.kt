package com.example.hotspot.view

import android.content.ContentValues
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.viewModels
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.example.hotspot.R
import com.example.hotspot.databinding.FragmentPersonalProfileBinding
import com.example.hotspot.model.UserProfile
import com.example.hotspot.viewModel.PersonalProfileViewModel

class PersonalProfile : Fragment(),View.OnClickListener {

    lateinit var navController: NavController
    private val userModel : PersonalProfileViewModel by viewModels()
    private var _binding: FragmentPersonalProfileBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentPersonalProfileBinding.inflate(inflater, container, false)
        val view = binding.root


        val user = userModel.getUserData().value
        updateProfileUI(user)

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = Navigation.findNavController(view)
        view.findViewById<Button>(R.id.fragment_personal_profile_editProfile_btn).setOnClickListener(this)

    }

    override fun onClick(p0: View?) {
        when (p0!!.id) {
            R.id.fragment_personal_profile_editProfile_btn -> navController.navigate(R.id.action_personalProfile_to_editProfile)


        }
    }



    override fun onResume() {
        super.onResume()

        userModel.getUserData().observe(this, {user ->
            updateProfileUI(user)
        })

    }


    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }




    fun updateProfileUI(userProfile: UserProfile?) {

        if (userProfile != null) {
            binding.fragmentPersonalProfilePersonName.text = userProfile.name
            binding.fragmentPersonalProfileAge.text = "${userProfile.age} år"
            binding.fragmentPersonalProfileBio.text = userProfile.bio




            if (userProfile.bitmapImg != null) {
                Log.i(ContentValues.TAG, "Here is the from Personal profile ${userProfile.bitmapImg}")
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
    }




}