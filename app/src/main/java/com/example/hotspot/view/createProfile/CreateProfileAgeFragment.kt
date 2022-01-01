package com.example.hotspot.view.createProfile

import android.app.DatePickerDialog
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
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
        binding.dateButton.setOnClickListener{
            initDatePicker()
        }

        binding.continueButton.setOnClickListener{
            findNavController().navigate(R.id.action_createProfileAgeFragment_to_createProfileGenderFragment)
        }


        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(CreateProfileAgeViewModel::class.java)
        // TODO: Use the ViewModel
    }

    private fun initDatePicker(){
        val c = Calendar.getInstance()
        val year = c.get(Calendar.YEAR)
        val month = c.get(Calendar.MONTH)
        val day = c.get(Calendar.DAY_OF_MONTH)

        val dpd = DatePickerDialog(this.requireContext(), DatePickerDialog.OnDateSetListener { view, myear, mMonth, mdayOfMonth ->
            val mmonth = mMonth +1

            if(mmonth < 10 && mdayOfMonth< 10) {
                binding.dateButton.setText("0" + mdayOfMonth + "/0" + mmonth + "/" + myear)
            }
            else if(mmonth < 10 ){
                binding.dateButton.setText("" + mdayOfMonth + "/0" + mmonth + "/" + myear)
            }
            else if(mdayOfMonth < 10){
                binding.dateButton.setText("0" + mdayOfMonth + "/" + mmonth + "/" + myear)
            }
            else
            binding.dateButton.setText("" + mdayOfMonth + "/" + mmonth + "/" + myear)
        }, year, month, day)

        dpd.show()
    }

}