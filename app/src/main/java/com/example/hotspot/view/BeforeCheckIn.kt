package com.example.hotspot.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.navigation.Navigation
import androidx.navigation.fragment.navArgs
import com.example.hotspot.R
import com.example.hotspot.databinding.BeforeCheckInBinding
import com.example.hotspot.databinding.FragmentMaps4Binding


class BeforeCheckIn : Fragment() {

    private val args: BeforeCheckInArgs by navArgs()
    private lateinit var binding: BeforeCheckInBinding



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?

    ): View? {

        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.before_check_in, container, false)
        binding = BeforeCheckInBinding.bind(view)


        view.findViewById<Button>(R.id.checkInButton1).setOnClickListener {

            Navigation.findNavController(view).navigate(R.id.afterCheckIn)
        }
        return view
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.eventLocationName.text = args.hotSpot.hotSpotName


    }



}