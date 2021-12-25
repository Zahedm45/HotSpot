package com.example.hotspot.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.viewModels
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.example.hotspot.R
import com.example.hotspot.databinding.FragmentPersonalProfileBinding
import com.example.hotspot.viewModel.UserViewModel

class PersonalProfile : Fragment(),View.OnClickListener {

    lateinit var navController: NavController
    private val userModel : UserViewModel by viewModels()
    private var _binding: FragmentPersonalProfileBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

       // val view = inflater.inflate(R.layout.fragment_personal_profile, container, false)
        _binding = FragmentPersonalProfileBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = Navigation.findNavController(view)
        view.findViewById<Button>(R.id.btn_edit_profile).setOnClickListener(this)
     //   view.findViewById<TextView>(R.id.personName_textView).text =
    }

    override fun onClick(p0: View?) {
        when (p0!!.id) {
            R.id.btn_edit_profile -> navController.navigate(R.id.action_personalProfile_to_editProfile)

//            R.id.btn_edit_profile -> userModel.getUpdate()


        }
    }

//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        binding = FragmentPersonalProfileBinding.inflate(layout)
//    }

    override fun onResume() {
        super.onResume()


        userModel.getUserData().observe(this, {user ->
           // view?.findViewById<TextView>(R.id.personName_textView)?.setText(user.name)
            view?.findViewById<TextView>(R.id.tv_age)?.setText(user.age.toString())
            binding.personNameTextView.text = "ddddd"


            // binding.personNameTextView.text = "hellooo...."
//            binding.personNameTextView.text = user.name
//            binding.tvAge.setText(user.age)
//            binding.personalComment.text = user.emailAddress




        })

    }


    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}