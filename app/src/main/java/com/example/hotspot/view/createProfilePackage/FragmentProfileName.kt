package com.example.hotspot.view.createProfilePackage

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.example.hotspot.R
import com.example.hotspot.databinding.CreateProfileNameFragmentBinding

class FragmentProfileName : Fragment() {


    private val viewModel: SharedViewModelCreateProfile by activityViewModels()

    private var _binding: CreateProfileNameFragmentBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = CreateProfileNameFragmentBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)


        binding.continueButton.setOnClickListener{
            val animation_up = AnimationUtils.loadAnimation(this.requireContext(), R.anim.scale_button_up)
            binding.continueButton.startAnimation(animation_up)
            val animation_down = AnimationUtils.loadAnimation(this.requireContext(), R.anim.scale_button_down)
            binding.continueButton.startAnimation(animation_down)

            val a : String
            a = binding.firstNameText.text.toString()
            viewModel.setName(binding.firstNameText.text.toString())
            findNavController().navigate(R.id.action_createProfileName_to_createProfileAgeFragment)
        }

        viewModel.getFirstName().observe(viewLifecycleOwner,
            Observer { binding.firstNameText.setText(it.toString())})

    }

}