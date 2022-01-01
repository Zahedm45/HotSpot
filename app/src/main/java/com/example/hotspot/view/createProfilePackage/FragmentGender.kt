package com.example.hotspot.view.createProfilePackage

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.hotspot.R
import com.example.hotspot.databinding.CreateProfileGenderFragmentBinding

class FragmentGender : Fragment() {

    private val viewModel: SharedViewModelCreateProfile by activityViewModels()

    private var _binding: CreateProfileGenderFragmentBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = CreateProfileGenderFragmentBinding.inflate(inflater, container, false)


        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)


        binding.manButton.setOnClickListener {
            viewModel.setGender("Male")
            findNavController().navigate(R.id.action_createProfileGenderFragment_to_createProfileUploadImageFragment)
        }

        binding.womanButton.setOnClickListener {
            viewModel.setGender("Female")
            findNavController().navigate(R.id.action_createProfileGenderFragment_to_createProfileUploadImageFragment)
        }

    }

}