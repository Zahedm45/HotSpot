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
import android.view.animation.AnimationUtils
import androidx.appcompat.app.AppCompatActivity
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
        val view = inflater.inflate(R.layout.fragment_edit_profile, container, false)

        (activity as AppCompatActivity?)!!.supportActionBar!!.apply {
            title = "Edit Profile"
//            setDisplayHomeAsUpEnabled(true)
//            setDisplayShowHomeEnabled(true)
        }


        return view
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentEditProfileBinding.bind(view)
        editProfileVM.getUserProfile(binding, this)



        binding.fragmentEdidProfileChangePicBtn.setOnClickListener {
            val animation_up = AnimationUtils.loadAnimation(this.requireContext(), R.anim.scale_button_up)
            binding.fragmentEdidProfileChangePicBtn.startAnimation(animation_up)
            val animation_down = AnimationUtils.loadAnimation(this.requireContext(), R.anim.scale_button_down)
            binding.fragmentEdidProfileChangePicBtn.startAnimation(animation_down)

            val intent = Intent(Intent.ACTION_PICK)
            intent.type = "image/*"
            startActivityForResult(intent, 0)
        }

        binding.editProfileSaveChangeBtn.setOnClickListener {
            val animation_up = AnimationUtils.loadAnimation(this.requireContext(), R.anim.scale_button_up)
            binding.editProfileSaveChangeBtn.startAnimation(animation_up)
            val animation_down = AnimationUtils.loadAnimation(this.requireContext(), R.anim.scale_button_down)
            binding.editProfileSaveChangeBtn.startAnimation(animation_down)

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