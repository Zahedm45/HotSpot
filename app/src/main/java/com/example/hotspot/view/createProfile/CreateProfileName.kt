package com.example.hotspot.view.createProfile

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.hotspot.R
import com.example.hotspot.databinding.CreateProfileNameFragmentBinding

class CreateProfileName : Fragment() {


    private lateinit var viewModel: CreateProfileSharedViewModel

    private var _binding: CreateProfileNameFragmentBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = CreateProfileNameFragmentBinding.inflate(inflater, container, false)
        binding.continueButton.setOnClickListener{
            findNavController().navigate(R.id.action_createProfileName_to_createProfileAgeFragment)
        }
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(CreateProfileSharedViewModel::class.java)

        // TODO: Use the ViewModel
    }

}