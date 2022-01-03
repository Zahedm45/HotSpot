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
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import com.example.hotspot.databinding.FragmentCreateProfileUploadImageBinding
import android.content.ContentResolver
import com.example.hotspot.view.AfterLoginActivity


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

        binding.continueButton.setOnClickListener {
            viewModel.createNewProfile( { ->
                updateUIOnSuccess()
            },
                {
                        msg -> updateUIOnFailure(msg)
                }
            )
        }


        return binding.root
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)


        if (resultCode == RESULT_OK && requestCode == pickImage) {
            imageUri = data?.data
            binding.uploadPhoto.setImageURI(imageUri)
            val bitMap = MediaStore.Images.Media.getBitmap(this.context?.contentResolver, imageUri)
            if(bitMap != null)
            viewModel.setImageUri(bitMap)
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)


        viewModel.getImage().observe(viewLifecycleOwner, Observer {
            binding.uploadPhoto.setImageBitmap(it)
            //Toast.makeText(this.requireContext(),"test", Toast.LENGTH_SHORT).show()
        })
    }

    override fun onResume() {
        super.onResume()
        //binding.uploadPhoto.setImageURI(viewModel.getImage().value)
        viewModel.getImage().observe(viewLifecycleOwner,  {
            binding.uploadPhoto.setImageBitmap(it)
            //Toast.makeText(this.requireContext(),"test", Toast.LENGTH_SHORT).show()
        })
    }

    override fun onStart() {
        super.onStart()
        viewModel.getImage().observe(viewLifecycleOwner, Observer {
            binding.uploadPhoto.setImageBitmap(it)
        })
    }

    private fun updateUIOnSuccess(){
        // signal to user that there was error
        Toast.makeText(this.requireContext(), "Success", Toast.LENGTH_SHORT).show()
        val intent = Intent(this.context, AfterLoginActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
        startActivity(intent)
    }

    private fun updateUIOnFailure(errorMessage: String){
        Toast.makeText(this.requireContext(), errorMessage, Toast.LENGTH_SHORT).show()
        // signal to user that there was an error
    }




}