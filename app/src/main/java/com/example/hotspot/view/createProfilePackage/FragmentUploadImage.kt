package com.example.hotspot.view.createProfilePackage

import android.app.Activity.RESULT_OK
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import com.example.hotspot.databinding.FragmentCreateProfileUploadImageBinding


class FragmentUploadImage : Fragment() {

    private val viewModel: SharedViewModelCreateProfile by activityViewModels()
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
            if(imageUri != null)
            viewModel.setImageUri(imageUri)
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)


        viewModel.getImage().observe(viewLifecycleOwner, Observer {
            binding.uploadPhoto.setImageURI(it)
            //Toast.makeText(this.requireContext(),"test", Toast.LENGTH_SHORT).show()
        })
    }

    override fun onResume() {
        super.onResume()
        //binding.uploadPhoto.setImageURI(viewModel.getImage().value)
        viewModel.getImage().observe(viewLifecycleOwner,  {
            binding.uploadPhoto.setImageURI(it)
            //Toast.makeText(this.requireContext(),"test", Toast.LENGTH_SHORT).show()
        })
    }

    override fun onStart() {
        super.onStart()
        viewModel.getImage().observe(viewLifecycleOwner, Observer {
            binding.uploadPhoto.setImageURI(it)
        })
    }


}