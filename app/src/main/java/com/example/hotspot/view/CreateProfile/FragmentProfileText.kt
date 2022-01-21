package com.example.hotspot.view.CreateProfile

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.example.hotspot.R
import com.example.hotspot.databinding.FragmentCreateProfileProfileTextBinding
import com.example.hotspot.other.util.ButtonAnimations


class FragmentProfileText : Fragment() {
    private val viewModel: SharedViewModelCreateProfile by activityViewModels()

    private var _binding: FragmentCreateProfileProfileTextBinding? = null
    private val binding get() = _binding!!

    private var isContinueClickable = false


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentCreateProfileProfileTextBinding.inflate(inflater, container, false)


        binding.continueButton.setOnClickListener {
            if(!isContinueClickable) return@setOnClickListener
            viewModel.setProfileText(binding.tvFirstNameText.text.toString())
            ButtonAnimations.clickButton(binding.continueButton)
            findNavController().navigate(R.id.action_fragmentProfileText_to_createProfileUploadImageFragment)
        }


        viewModel.getProfileText().observe(viewLifecycleOwner,
            Observer {
                binding.tvFirstNameText.setText(it.toString())
            })



        binding.tvFirstNameText.addTextChangedListener() {
            if(it.toString().isNotEmpty()){
                ButtonAnimations.fadeIn(binding.continueButton)
                isContinueClickable = true
                Log.d("Empty", "Empty")
            }
            else{
                ButtonAnimations.fadeOut(binding.continueButton)
                isContinueClickable = false
                Log.d("not", "not")
            }
        }


        return binding.root
    }


}