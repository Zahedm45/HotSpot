package com.example.hotspot.view.Profile

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.hotspot.R
import com.example.hotspot.databinding.FragmentProfileFromCheckInBinding
import com.example.hotspot.other.util.ButtonAnimations
import kotlinx.android.synthetic.main.fragment_profile_from_check_in.*


class FragmentOthersProfile : Fragment() {

   // lateinit var navController: NavController
    private val args: FragmentOthersProfileArgs by navArgs()
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

        binding.profileFromCheckInButton.setOnClickListener {
            Log.d("OthersProfile","Clicked on Chat Button")
            ButtonAnimations.clickButton(profile_from_check_in_button)
            val username = args.user.name!!
            val userid = args.user.uid!!
            val action = FragmentOthersProfileDirections.actionOthersProfileToChatlog(userid,username)
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