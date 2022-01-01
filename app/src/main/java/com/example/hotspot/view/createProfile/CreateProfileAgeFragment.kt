package com.example.hotspot.view.createProfile

import android.app.DatePickerDialog
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.hotspot.R
import com.example.hotspot.databinding.CreateProfileAgeFragmentBinding
import com.example.hotspot.databinding.CreateProfileNameFragmentBinding
import java.util.*

class CreateProfileAgeFragment : Fragment() {

    private var _binding: CreateProfileAgeFragmentBinding? = null
    private val binding get() = _binding!!

    private lateinit var datePicker: DatePickerDialog

    private lateinit var viewModel: CreateProfileAgeViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = CreateProfileAgeFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(CreateProfileAgeViewModel::class.java)
        // TODO: Use the ViewModel
    }

    private fun initDatePicker(){

    }

    private fun openDatePicker(){

    }
}