package com.example.hotspot.view.CreateProfile

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.hotspot.R
import com.example.hotspot.databinding.FragmentCreateProfileGenderBinding
import com.example.hotspot.other.util.ButtonAnimations

class FragmentGender : Fragment() {

    private val viewModel: SharedViewModelCreateProfile by activityViewModels()

    private var _binding: FragmentCreateProfileGenderBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCreateProfileGenderBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)


        binding.manButton.setOnClickListener {
            viewModel.setGender("Male")
            ButtonAnimations.clickButton(binding.manButton)
            findNavController().navigate(R.id.action_createProfileGenderFragment_to_fragmentProfileText)
        }

        binding.womanButton.setOnClickListener {
            viewModel.setGender("Female")
            ButtonAnimations.clickButton(binding.womanButton)
            findNavController().navigate(R.id.action_createProfileGenderFragment_to_fragmentProfileText)
        }

    }

}