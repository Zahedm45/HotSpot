package com.example.hotspot.view

import android.os.Bundle
import android.util.Log
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
import com.example.hotspot.model.User
import com.example.hotspot.other.ButtonAnimations
import kotlinx.android.synthetic.main.fragment_chatlog.*
import kotlinx.android.synthetic.main.fragment_profile_from_check_in.*


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

        profile_from_check_in_button.setOnClickListener {
            Log.d("OthersProfile","Clicked on Chat Button")
            ButtonAnimations.clickButton(profile_from_check_in_button)
            val username = args.user.name!!
            val userid = args.user.uid!!
            val action = OthersProfileDirections.actionOthersProfileToChatlog(userid,username)
            findNavController().navigate(action)
        }




    }





    private fun setAllAttributes() {
        var user = args.user
        binding.profileFromCheckInPic.setImageBitmap(user.bitmapImg)
        binding.profileFromCheckInName.text = user.name
        binding.profileFromCheckInAge.text = user.age.toString()
        binding.profileFromCheckInBio.text = user.bio

    }

}