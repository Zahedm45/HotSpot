package com.example.hotspot.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.hotspot.R
import com.example.hotspot.databinding.FragmentProfileFromCheckInBinding


class OthersProfile : Fragment() {

   // lateinit var navController: NavController
    private val args: OthersProfileArgs by navArgs()
    lateinit var binding: FragmentProfileFromCheckInBinding


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_profile_from_check_in, container, false)
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
       // navController = Navigation.findNavController(view)
        binding = FragmentProfileFromCheckInBinding.bind(view)

        setAllAttributes()




    }





    private fun setAllAttributes() {
        var user = args.user
        binding.profileFromCheckInPic.setImageBitmap(user.bitmapImg)
        binding.profileFromCheckInName.text = user.name
        binding.profileFromCheckInAge.text = user.age.toString()
        binding.profileFromCheckInBio.text = user.bio

    }

}