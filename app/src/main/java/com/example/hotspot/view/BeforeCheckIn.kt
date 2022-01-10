package com.example.hotspot.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.navigation.Navigation
import com.example.hotspot.R


class BeforeCheckIn : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?

    ): View? {

        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.before_check_in, container, false)

        view.findViewById<Button>(R.id.checkInButton1).setOnClickListener {

            Navigation.findNavController(view).navigate(R.id.afterCheckIn)
        }
        return view
    }

}