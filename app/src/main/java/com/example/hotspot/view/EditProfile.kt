package com.example.hotspot.view

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import android.provider.MediaStore
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.hotspot.R
import com.example.hotspot.databinding.FragmentEditProfileBinding
import com.example.hotspot.viewModel.EditProfileVM

class EditProfile : Fragment(R.layout.fragment_edit_profile) {

    private lateinit var binding: FragmentEditProfileBinding
    private val editProfileVM = EditProfileVM
    var bitmap: Bitmap? = null




    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_edit_profile, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentEditProfileBinding.bind(view)
        editProfileVM.getUserProfile(binding)



        binding.fragmentEdidProfileChangePicBtn.setOnClickListener {
            val intent = Intent(Intent.ACTION_PICK)
            intent.type = "image/*"
            startActivityForResult(intent, 0)
        }

        binding.editProfileSaveChangeBtn.setOnClickListener {
           // Log.i(TAG, "Clicked...")
            editProfileVM.updateUserProfile(bitmap)
        }


    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == 0 && resultCode == Activity.RESULT_OK && data != null) {

            val uri = data.data
            bitmap = MediaStore.Images.Media.getBitmap(context?.contentResolver, uri)
            binding.fragmentEditProfileEditPicture.setImageBitmap(bitmap)
        }
    }



}