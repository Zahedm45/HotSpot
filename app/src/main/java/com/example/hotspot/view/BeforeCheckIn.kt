package com.example.hotspot.view

import android.annotation.SuppressLint
import android.graphics.Paint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.navigation.fragment.navArgs
import com.example.hotspot.R
import com.example.hotspot.databinding.BeforeCheckInBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch



class BeforeCheckIn : Fragment() {

    private val args: BeforeCheckInArgs by navArgs()
    private lateinit var binding: BeforeCheckInBinding



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?

    ): View? {

        val view = inflater.inflate(R.layout.before_check_in, container, false)
        return view
    }




    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = BeforeCheckInBinding.bind(view)

        setAllInfo()
        heartButton()
        checkInBtn(view)

    }








    private fun setAllInfo() {
        binding.beforeCheckInEventLocationName.text = args.hotSpot.name
        var checkedIn = args.hotSpot.checkedIn?.size

        if (checkedIn == null) {
            checkedIn = 0
        }
        binding.beforeCheckInCheckedIn.text = "Checked in: ${checkedIn.toString()}"
        binding.beforeCheckInRatingBar.rating = args.hotSpot.rating!!.toFloat()
        binding.beforeCheckInReviews.paintFlags = Paint.UNDERLINE_TEXT_FLAG

      //  binding.beforeCheckInReviews.setPaintFlags(binding.beforeCheckInReviews.getPaintFlags() or Paint.UNDERLINE_TEXT_FLAG)
        // binding.beforeCheckInReviews.paintFlags =  Paint.UNDERLINE_TEXT_FLAG or binding.beforeCheckInReviews.paintFlags
        //  binding.beforeCheckInReviews.setPaintFlags(binding.beforeCheckInReviews.getPaintFlags() or Paint.UNDERLINE_TEXT_FLAG)


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




    private fun heartButton() {
        binding.beforeCheckInFavoriteBtnWhite.setOnClickListener {
            binding.beforeCheckInFavoriteBtnWhite.visibility = View.GONE
            binding.beforeCheckInFavoriteBtnThemeColor.visibility = View.VISIBLE
        }

        binding.beforeCheckInFavoriteBtnThemeColor.setOnClickListener {
            binding.beforeCheckInFavoriteBtnThemeColor.visibility = View.GONE
            binding.beforeCheckInFavoriteBtnWhite.visibility = View.VISIBLE

        }
    }


    @SuppressLint("SetTextI18n", "UseCompatLoadingForDrawables")
    private fun checkInBtn(view: View) {
        binding.beforeCheckInCheckInBtn.setOnClickListener {
           // BeforeCheckInVM.setCheckedIn(args.hotSpot.)

            CoroutineScope(IO).launch {
                val drawable = resources.getDrawable(R.drawable.custom_button_click_effect)
                binding.beforeCheckInCheckInBtn.background = drawable

                delay(100)
                CoroutineScope(Main).launch {

                    val drawable2 = resources.getDrawable(R.drawable.custom_button)
                    binding.beforeCheckInCheckInBtn.background = drawable2


                    val action = BeforeCheckInDirections.actionBeforeCheckInToAfterCheckIn(args.hotSpot)
                    view.findNavController().navigate(action)


                   // Navigation.findNavController(view).navigate(R.id.afterCheckIn)
                }
            }
        }

    }



}

















