package com.example.hotspot.view

import android.opengl.Visibility
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.hotspot.R
import com.example.hotspot.databinding.BeforeCheckInBinding
import com.example.hotspot.databinding.FragmentAfterCheckInBinding

class AfterCheckIn : Fragment() {

    private lateinit var binding: FragmentAfterCheckInBinding


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_after_check_in, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentAfterCheckInBinding.bind(view)


        binding.afterCheckInFavoriteBtnWhite.setOnClickListener {
            binding.afterCheckInFavoriteBtnWhite.visibility = View.GONE
            binding.afterCheckInFavoriteBtnThemeColor.visibility = View.VISIBLE

        }


        binding.afterCheckInFavoriteBtnThemeColor.setOnClickListener {
            binding.afterCheckInFavoriteBtnThemeColor.visibility = View.GONE
            binding.afterCheckInFavoriteBtnWhite.visibility = View.VISIBLE

        }






    }

}