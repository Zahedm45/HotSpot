package com.example.hotspot.view.CreateProfile

import android.app.DatePickerDialog
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.hotspot.R
import com.example.hotspot.databinding.FragmentCreateProfileAgeBinding
import com.example.hotspot.other.util.ButtonAnimations
import java.util.*

class FragmentAge : Fragment() {

    private var _binding: FragmentCreateProfileAgeBinding? = null
    private val binding get() = _binding!!

    private lateinit var datePicker: DatePickerDialog

    private val viewModel: SharedViewModelCreateProfile by activityViewModels()

    private var cday: Int = 0
    private var cmonth: Int = 0
    private var cyear: Int = 0

    private var isContinueClickable = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentCreateProfileAgeBinding.inflate(inflater, container, false)


        binding.continueButton.setOnClickListener{
            if(!isContinueClickable) return@setOnClickListener
            ButtonAnimations.clickButton(binding.continueButton)
            findNavController().navigate(R.id.action_createProfileAgeFragment_to_fragmentEmail)
        }


        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)


        binding.dateButton.setOnClickListener{
            initDatePicker()

        }

        viewModel.getDateString().observe(viewLifecycleOwner, androidx.lifecycle.Observer {
            binding.dateButton.text = it.toString()
            if(it.toString().isNotEmpty()){
                ButtonAnimations.fadeIn(binding.continueButton)
                isContinueClickable = true
                binding.dateButton.setText(it.toString())
                Log.d("Empty", "Empty")
            }
            else{
                ButtonAnimations.fadeOut(binding.continueButton)
                isContinueClickable = false
                Log.d("not", "not")
            }
        })

    }


    private fun initDatePicker(){
        val c = Calendar.getInstance()
        val year = c.get(Calendar.YEAR)
        val month = c.get(Calendar.MONTH)
        val day = c.get(Calendar.DAY_OF_MONTH)

        val dpd = DatePickerDialog(this.requireContext(), DatePickerDialog.OnDateSetListener { view, myear, mMonth, mdayOfMonth ->
            val mmonth = mMonth +1
            setDateForDatabase(mdayOfMonth,mmonth,myear)
            val dateString = setDateString(mdayOfMonth, mmonth,myear)
            binding.dateButton.setText(dateString)
            viewModel.setdateString(dateString)
            viewModel.setDayOfBirth(mdayOfMonth)
            viewModel.setMonth(mmonth)
            viewModel.setYear(myear)
        }, year, month, day)

        dpd.show()
    }

    private fun setDateForDatabase(xday : Int, xmonth : Int, xyear : Int){
        cday = xday
        cmonth = xmonth
        cyear = xyear
    }

    private fun setDateString(day : Int, month : Int, year : Int): String{
        if(month < 10 && day< 10) {
            return "0$day/0$month/$year"
        }
        else if(month < 10 ){
            return "$day/0$month/$year"
        }
        else if(day < 10){
            return "0$day/$month/$year"
        }
        else
            return "$day/$month/$year"
        return "null"
    }

}