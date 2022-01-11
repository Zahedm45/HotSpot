package com.example.hotspot.view

import android.annotation.SuppressLint
import android.graphics.Paint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.navigation.Navigation
import androidx.navigation.fragment.navArgs
import com.example.hotspot.R
import com.example.hotspot.databinding.BeforeCheckInBinding
import java.util.*



class BeforeCheckIn : Fragment() {

    private val args: BeforeCheckInArgs by navArgs()
    private lateinit var binding: BeforeCheckInBinding



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?

    ): View? {

        val view = inflater.inflate(R.layout.before_check_in, container, false)

        view.findViewById<Button>(R.id.before_check_in_check_in_btn).setOnClickListener {
            Navigation.findNavController(view).navigate(R.id.afterCheckIn)
        }
        return view
    }


    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = BeforeCheckInBinding.bind(view)

        setAllInfo()


    }






    private fun setAllInfo() {
        binding.beforeCheckInEventLocationName.text = args.hotSpot.hotSpotName
        var checkedIn = args.hotSpot.checkedIn?.size

        if (checkedIn == null) {
            checkedIn = 0
        }
        binding.beforeCheckInCheckedIn.text = "Checked in: ${checkedIn.toString()}"
        binding.beforeCheckInRatingBar.rating = args.hotSpot.overallRating!!.toFloat()
        binding.beforeCheckInReviews.paintFlags = Paint.UNDERLINE_TEXT_FLAG
        binding.beforeCheckInDescriptionTv.text = getAddress()
    }






    private fun getAddress(): String {

        val streetName = checkIfNull(args.hotSpot.address?.streetName)
        val doorNum = checkIfNull(args.hotSpot.address?.doorNumber)
        val floor = checkIfNull(args.hotSpot.address?.floor)
        val town = checkIfNull(args.hotSpot.address?.town)
        val postalCode = checkIfNull(args.hotSpot.address?.postalCode)
        val country = checkIfNull(args.hotSpot.address?.country)

//        if (floor == doorNum && floor >) {
//            return "$streetName $doorNum \n$postalCode $town \n$country"
//        }
        return "$streetName $doorNum, $floor \n$postalCode $town \n$country"
    }



    private fun checkIfNull(str: String?): String {
        if (str == "null" || str == null || str == "Vej uden navn") {
           return ""
        }

        return str
    }



}

















