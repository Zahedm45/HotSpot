package com.example.hotspot.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import androidx.navigation.Navigation
import com.example.hotspot.R

class HomeMap : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_home_map, container, false)

        view.findViewById<ImageButton>(R.id.mapImage).setOnClickListener {

            Navigation.findNavController(view).navigate(R.id.beforeCheckIn)
        }
        return view

    }
}