package com.example.hotspot.view


import android.content.ContentValues.TAG
import android.graphics.Color
import android.graphics.Paint
import android.location.Address
import android.location.Geocoder
import android.os.Bundle
import android.text.method.LinkMovementMethod
import android.util.Log
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

        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.before_check_in, container, false)
        binding = BeforeCheckInBinding.bind(view)

        view.findViewById<Button>(R.id.before_check_in_check_in_btn).setOnClickListener {

            Navigation.findNavController(view).navigate(R.id.afterCheckIn)
        }
        return view
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        val checkedIn = args.hotSpot.checkedIn?.size

        binding.beforeCheckInEventLocationName.text = args.hotSpot.hotSpotName
        binding.beforeCheckInCheckedIn.text = "Checked in: ${checkedIn.toString()}"
        binding.beforeCheckInDescriptionTv.text = getAddress()
        binding.beforeCheckInRatingBar.rating = args.hotSpot.overallRating!!.toFloat()

        binding.beforeCheckInReviews.paintFlags = binding.beforeCheckInReviews.paintFlags or Paint.UNDERLINE_TEXT_FLAG

        binding.beforeCheckInReviews.setOnClickListener {
            Log.i(TAG, "Helloel..")
        }

//        binding.beforeCheckInReviews.setMovementMethod(LinkMovementMethod.getInstance())
//
//        binding.beforeCheckInReviews.setHighlightColor(Color.TRANSPARENT)


    }







    private fun getAddress(): String {

        val lat = args.hotSpot.address?.latitude
        val lng = args.hotSpot.address?.longitude
        val realAddress: List<Address>
        val geocoder = Geocoder(requireContext(), Locale.getDefault())

        if (lat != null && lng != null) {
            realAddress = geocoder.getFromLocation(lat, lng, 1)

        } else {
            return "Address not found"
        }

        // val address = realAddress.get(0).getAddressLine(0)

        val roadName = realAddress.get(0).thoroughfare
        val doorNum = realAddress.get(0).subThoroughfare
        val floor = realAddress.get(0).featureName
        val town = realAddress.get(0).subLocality
        val postalCode = realAddress.get(0).postalCode
        val country = realAddress.get(0).countryName


        return "$roadName $doorNum, $floor \n$postalCode $town \n$country"
    }


}