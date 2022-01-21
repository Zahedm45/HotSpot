package com.example.hotspot.view.Profile

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import android.widget.Button
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.example.hotspot.R
import com.example.hotspot.databinding.FragmentPersonalProfileBinding
import com.example.hotspot.other.util.ButtonAnimations
import com.example.hotspot.other.util.UtilView
import com.example.hotspot.viewModel.PersonalProfileVM
import kotlinx.android.synthetic.main.fragment_personal_profile.*


class FragmentPersonalProfile : Fragment(),View.OnClickListener {

    lateinit var navController: NavController
//    private val userModel : PersonalProfileVM by viewModels()
    private var _binding: FragmentPersonalProfileBinding? = null
    private val binding get() = _binding!!

   // lateinit var personalProfileVM: PersonalProfileVM

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentPersonalProfileBinding.inflate(inflater, container, false)
        val view = binding.root


        PersonalProfileVM.getProfile(binding)



        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = Navigation.findNavController(view)
        view.findViewById<Button>(R.id.fragment_personal_profile_editProfile_btn).setOnClickListener(this)

    }

    override fun onClick(p0: View?) {
        when (p0!!.id) {
            R.id.fragment_personal_profile_editProfile_btn -> {
                ButtonAnimations.clickButton(fragment_personal_profile_editProfile_btn)
                navController.navigate(R.id.action_personalProfile_to_editProfile)
            }
        }
    }


    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)

    }



    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.nav_top_menu, menu)

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        UtilView.menuOptionClick(item, requireActivity())
        return super.onOptionsItemSelected(item)
    }


    override fun onDetach() {
        super.onDetach()
        PersonalProfileVM.getUserProfileReg?.remove()

    }



}