package com.example.hotspot.view

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Color
import android.graphics.LinearGradient
import android.graphics.Shader
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.findNavController
import com.example.hotspot.R
import com.example.hotspot.databinding.FragmentEditProfileBinding
import com.example.hotspot.other.UtilView
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
        binding = FragmentEditProfileBinding.inflate(inflater,container, false)



        return view
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentEditProfileBinding.bind(view)
        editProfileVM.getUserProfile(binding, this)



        binding.fragmentEdidProfileChangePicBtn.setOnClickListener {
            val intent = Intent(Intent.ACTION_PICK)
            intent.type = "image/*"
            startActivityForResult(intent, 0)
        }

        binding.tvSignOut.setOnClickListener{
            UtilView.signOut(this.requireActivity())
        }

        binding.tvDone.setOnClickListener {
           // Log.i(TAG, "Clicked...")
            editProfileVM.updateUserProfile(bitmap)
            findNavController().navigate(R.id.action_editProfile_to_personalProfile)
        }
        setGradientColor()


    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == 0 && resultCode == Activity.RESULT_OK && data != null) {

            val uri = data.data
            bitmap = MediaStore.Images.Media.getBitmap(context?.contentResolver, uri)
            binding.fragmentEditProfileEditPicture.setImageBitmap(bitmap)
        }
    }

    private fun setGradientColor(){
        val paint = binding.tvDone.paint
        val width = paint.measureText(binding.tvDone.text.toString())
        val textShader = LinearGradient(0f, 0f, width, binding.tvDone.textSize,
            this.resources.getColor(R.color.orange_hotspot),
            this.resources.getColor(R.color.purple_hotspot),
            Shader.TileMode.CLAMP
        )

        binding.tvDone.paint.setShader(textShader)
        binding.tvDone.setTextColor(Color.RED)
        Log.d("is this being called?", "is it?")
    }

}