package com.example.hotspot.view.createProfilePackage

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
import com.example.hotspot.databinding.CreateProfileNameFragmentBinding
import com.example.hotspot.other.ButtonAnimations

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
            viewModel.setName(binding.firstNameText.text.toString())
            findNavController().navigate(R.id.action_createProfileName_to_createProfileAgeFragment)
        }

        viewModel.getFirstName().observe(viewLifecycleOwner,
            Observer {
                binding.firstNameText.setText(it.toString())
            })

        binding.firstNameText.addTextChangedListener {
            if(it.toString().isNotEmpty()){
                ButtonAnimations.fadeIn(binding.continueButton)
                Log.d("Empty", "Empty")
            }
            else{
                ButtonAnimations.fadeOut(binding.continueButton)
                Log.d("not", "not")
            }
        }

    }



}