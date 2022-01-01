package com.example.hotspot.view.createProfile

import android.app.Activity.RESULT_OK
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.example.hotspot.R
import com.example.hotspot.databinding.CreateProfileGenderFragmentBinding
import com.example.hotspot.databinding.FragmentCreateProfileUploadImageBinding


class CreateProfileUploadImageFragment : Fragment() {

    private lateinit var viewModel: CreateProfileSharedViewModel
    private var _binding: FragmentCreateProfileUploadImageBinding? = null
    private val binding get() = _binding!!

    private val pickImage = 100
    private var imageUri: Uri? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCreateProfileUploadImageBinding.inflate(inflater, container, false)
        binding.uploadPhoto.setOnClickListener{
            val gallery = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI)
            startActivityForResult(gallery, pickImage)
        }

        return binding.root
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == RESULT_OK && requestCode == pickImage) {
            imageUri = data?.data
            binding.uploadPhoto.setImageURI(imageUri)
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(CreateProfileSharedViewModel::class.java)



        // TODO: Use the ViewModel
    }


}