package com.example.hotspot.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.example.hotspot.R

class LogIn : Fragment() {

    lateinit var navController : NavController


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_log_in, container, false)

//        view.findViewById<Button>(R.id.login_btn).setOnClickListener {
//            Navigation.findNavController(view).navigate(R.id.homeMapeCop)
//        }

        return view;

    }
//    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
//        super.onViewCreated(view, savedInstanceState)
//        navController = Navigation.findNavController(view)
//        view.findViewById<Button>(R.id.login_btn).setOnClickListener(this)
//
//    }
//    override fun onClick(p0: View?) {
//        when (p0!!.id) {
//            R.id.login_btn -> navController.navigate(R.id.homeMapeCop)
//        }
//    }
}