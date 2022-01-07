package com.example.hotspot.view.createProfilePackage

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
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
            val animation_up = AnimationUtils.loadAnimation(this.requireContext(), R.anim.scale_button_up)
            binding.manButton.startAnimation(animation_up)
            val animation_down = AnimationUtils.loadAnimation(this.requireContext(), R.anim.scale_button_down)
            binding.manButton.startAnimation(animation_down)
            viewModel.setGender("Male")
            findNavController().navigate(R.id.action_createProfileGenderFragment_to_fragmentProfileText)
        }

        binding.womanButton.setOnClickListener {
            val animation_up = AnimationUtils.loadAnimation(this.requireContext(), R.anim.scale_button_up)
            binding.womanButton.startAnimation(animation_up)
            val animation_down = AnimationUtils.loadAnimation(this.requireContext(), R.anim.scale_button_down)
            binding.womanButton.startAnimation(animation_down)
            viewModel.setGender("Female")
            findNavController().navigate(R.id.action_createProfileGenderFragment_to_fragmentProfileText)
        }

    }

}