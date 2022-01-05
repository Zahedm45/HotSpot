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
            /*
            Gonna have to change something so I get the button to make animations

            code:
             val animation_up = AnimationUtils.loadAnimation(this, R.anim.scale_button_up)
            binding.checkInButton1.startAnimation(animation_up)
            val animation_down = AnimationUtils.loadAnimation(this, R.anim.scale_button_down)
            binding.checkInButton1.startAnimation(animation_down)


            */

            Navigation.findNavController(view).navigate(R.id.afterCheckIn)
        }
        return view
    }

}