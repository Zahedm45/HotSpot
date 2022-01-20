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
import com.example.hotspot.databinding.FragmentCreateProfileEmailBinding
import com.example.hotspot.other.util.ButtonAnimations

class FragmentEmail : Fragment() {


    private val viewModel: SharedViewModelCreateProfile by activityViewModels()

    private var _binding: FragmentCreateProfileEmailBinding? = null
    private val binding get() = _binding!!

    private var isContinueClickable = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentCreateProfileEmailBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)


        binding.continueButton.setOnClickListener{
            if(!isContinueClickable) return@setOnClickListener
            viewModel.setEmail(binding.tvEmail.text.toString())
            ButtonAnimations.clickButton(binding.continueButton)
            findNavController().navigate(R.id.action_fragmentEmail_to_createProfileGenderFragment)
        }

        viewModel.getEmail().observe(viewLifecycleOwner,
            Observer {
                binding.tvEmail.setText(it.toString())
            })

        binding.tvEmail.addTextChangedListener() {
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

    }



}