package com.example.hotspot.view.createProfile

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.hotspot.R
import com.example.hotspot.databinding.CreateProfileGenderFragmentBinding
import com.example.hotspot.databinding.FragmentCreateProfileUploadImageBinding


class CreateProfileUploadImageFragment : Fragment() {


    private var _binding: FragmentCreateProfileUploadImageBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCreateProfileUploadImageBinding.inflate(inflater, container, false)
        // Inflate the layout for this fragment

        return binding.root
    }


}