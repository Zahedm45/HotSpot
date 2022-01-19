package com.example.hotspot.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.hotspot.R

import com.example.hotspot.databinding.FragmentChatBinding
import kotlinx.android.synthetic.main.activity_after_login.*

class Chat : Fragment() {

    private var _binding: FragmentChatBinding? = null
    private val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentChatBinding.inflate(inflater, container, false)

        this.requireActivity().bottomNavigationView.visibility = View.GONE
        // Inflate the layout for this fragment
        return binding.root
    }


}