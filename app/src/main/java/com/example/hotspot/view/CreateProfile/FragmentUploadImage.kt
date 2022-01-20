package com.example.hotspot.view.CreateProfile

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
import android.content.Intent.FLAG_ACTIVITY_CLEAR_TOP
import android.util.Log
import com.example.hotspot.other.util.ButtonAnimations
import com.example.hotspot.other.util.DialogWifi
import com.example.hotspot.other.network.ConnectionLiveData
import com.example.hotspot.view.ActivityAfterLogin


class FragmentUploadImage : Fragment() {

    private val viewModel: SharedViewModelCreateProfile by activityViewModels()
    private var _binding: FragmentCreateProfileUploadImageBinding? = null
    private val binding get() = _binding!!
    private lateinit var connectionLiveData: ConnectionLiveData

    private val pickImage = 100
    private var imageUri: Uri? = null

    private var isSubmitClickable = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCreateProfileUploadImageBinding.inflate(inflater, container, false)
        binding.uploadPhoto.setOnClickListener{
            val gallery = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI)
            startActivityForResult(gallery, pickImage)
        }

        binding.progressBarIndeterminate.visibility = View.GONE


        connectionLiveData = ConnectionLiveData(this.requireContext())

        connectionLiveData.observe(this.viewLifecycleOwner, { isConnected ->
            binding.continueButton.setOnClickListener {
                if(!isSubmitClickable) return@setOnClickListener
                if(!isConnected) {
                    DialogWifi().show(this.childFragmentManager, com.example.hotspot.other.network.TAG)
                    return@setOnClickListener
                }
                ButtonAnimations.clickButton(binding.continueButton)
                binding.progressBar.visibility = View.GONE
                binding.progressBarIndeterminate.visibility = View.VISIBLE
                viewModel.createNewProfile( { ->
                    updateUIOnSuccess()
                },
                    {
                            msg -> updateUIOnFailure(msg)
                    }
                )
            }
        })

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

            if(it.toString().isNotEmpty()){
                ButtonAnimations.fadeIn(binding.continueButton)
                isSubmitClickable = true
                Log.d("Empty", "Empty")
            }
            else{
                ButtonAnimations.fadeOut(binding.continueButton)
                isSubmitClickable = false
                Log.d("not", "not")
            }
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
        val intent = Intent(this.context, ActivityAfterLogin::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
        intent.addFlags(FLAG_ACTIVITY_CLEAR_TOP)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
        startActivity(intent)
    }

    private fun updateUIOnFailure(errorMessage: String){
        Toast.makeText(this.requireContext(), errorMessage, Toast.LENGTH_SHORT).show()
        // signal to user that there was an error
    }




}