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


        val checkedIn = args.hotSpot.checkedIn?.size

        binding.beforeCheckInEventLocationName.text = args.hotSpot.hotSpotName
        binding.beforeCheckInCheckedIn.text = "Checked in: ${checkedIn.toString()}"
        binding.beforeCheckInRatingBar.rating = args.hotSpot.overallRating!!.toFloat()
        binding.beforeCheckInReviews.paintFlags = Paint.UNDERLINE_TEXT_FLAG



        val streetName = args.hotSpot.address?.streetName
        val doorNum = args.hotSpot.address?.doorNumber
        val floor = args.hotSpot.address?.floor
        val town = args.hotSpot.address?.town
        val postalCode = args.hotSpot.address?.postalCode
        val country = args.hotSpot.address?.country
        val address = "$streetName $doorNum, $floor \n$postalCode $town \n$country"

        binding.beforeCheckInDescriptionTv.text = address



    }


  /*  private suspend fun getAddress() {

        val lat = args.hotSpot.geoPoint?.latitude
        val lng = args.hotSpot.geoPoint?.longitude
        val geocoder = Geocoder(requireContext(), Locale.getDefault())


        lat?.let { lng?.let {

            geocoder.let {

                it.getFromLocation(lat, lng, 1)?.let { addr ->

                    val roadName = addr[0].thoroughfare
                    val doorNum = addr[0].subThoroughfare
                    val floor = addr[0].featureName
                    val town = addr[0].subLocality
                    val postalCode = addr[0].postalCode
                    val country = addr[0].countryName
                    val address = "$roadName $doorNum, $floor \n$postalCode $town \n$country"
                    setInMainTread(address)
                }
            }
        } }





        run {
            lat ?: return@run null
            lng ?: return@run null
            return@run lat + lng
        } ?: run {

            geocoder.let {

                it.getFromLocation(lat, lng, 1)?.let { addr ->

                    val roadName = addr[0].thoroughfare
                    val doorNum = addr[0].subThoroughfare
                    val floor = addr[0].featureName
                    val town = addr[0].subLocality
                    val postalCode = addr[0].postalCode
                    val country = addr[0].countryName
                    val address = "$roadName $doorNum, $floor \n$postalCode $town \n$country"
                    setInMainTread(address)
                }
            }
        }



        // val address = realAddress.get(0).getAddressLine(0)
    }


    private suspend fun setInMainTread(address: String) {

        CoroutineScope(Main).launch {
            binding.beforeCheckInDescriptionTv.text = address
        }
    }
*/

}

















