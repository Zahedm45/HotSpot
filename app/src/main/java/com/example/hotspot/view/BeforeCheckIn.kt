package com.example.hotspot.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.animation.AnimationUtils
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.navigation.Navigation
import com.example.hotspot.R
import com.example.hotspot.databinding.ActivityPhoneAuthBinding
import com.example.hotspot.databinding.BeforeCheckInBinding
import com.example.hotspot.databinding.FragmentEditProfileBinding


class BeforeCheckIn : Fragment() {
    private lateinit var binding: BeforeCheckInBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = BeforeCheckInBinding.inflate(layoutInflater)
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.before_check_in, container, false)
        binding.checkInButton1.setOnClickListener {
            val animation_up = AnimationUtils.loadAnimation(this.requireContext(), R.anim.scale_button_up)
            binding.checkInButton1.startAnimation(animation_up)
            val animation_down = AnimationUtils.loadAnimation(this.requireContext(), R.anim.scale_button_down)
            binding.checkInButton1.startAnimation(animation_down)

            Navigation.findNavController(view).navigate(R.id.afterCheckIn)
        }
        return view
    }

}