package com.example.hotspot

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.example.hotspot.databinding.BeforeCheckInBinding



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








//class  BeforeCheckIn : Fragment(), View.OnClickListener {
//
//    lateinit var navController: NavController
//
//    override fun onCreateView(
//        inflater: LayoutInflater, container: ViewGroup?,
//        savedInstanceState: Bundle?
//    ): View? {
//        return inflater.inflate(R.layout.before_check_in, container, false)
//    }
//
//    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
//        super.onViewCreated(view, savedInstanceState)
//        navController = Navigation.findNavController(view)
//        view.findViewById<Button>(R.id.checkInButton1).setOnClickListener(this)
//    }
//
//    override fun onClick(p0: View?) {
//        when(p0!!.id){
//            R.id.checkInButton1 -> navController.navigate(R.id.action_beforeCheckIn_to_afterCheckIn)
//        }
//    }
//
//}









