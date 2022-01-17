package com.example.hotspot.view

import android.annotation.SuppressLint
import android.graphics.Paint
import android.location.Location
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.compose.foundation.shape.CircleShape
import androidx.navigation.findNavController
import androidx.navigation.fragment.navArgs
import com.example.hotspot.R
import com.example.hotspot.databinding.BeforeCheckInBinding
import com.example.hotspot.model.CheckedInDB
import com.example.hotspot.other.network.TAG
import com.example.hotspot.other.service.MapService
import com.example.hotspot.view.Constant.CHECKED_IN
import com.example.hotspot.view.Constant.RADIUS
import com.example.hotspot.view.Constant.STREET_WITHOUT_NAME
import com.example.hotspot.viewModel.BeforeCheckInVM
import com.example.hotspot.viewModel.DataHolder
import com.example.hotspot.viewModel.UsersAndIds
import com.google.android.gms.maps.model.Circle
import com.google.android.gms.maps.model.CircleOptions
import com.google.android.gms.maps.model.LatLng
import kotlinx.android.synthetic.main.custom_toast_layout.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.math.pow

object Constant {
    const val STREET_WITHOUT_NAME = "Vej uden navn"
    const val CHECKED_IN = "Checked in: "
    const val RADIUS = 10.0
}

class BeforeCheckIn : Fragment() {

    private val args: BeforeCheckInArgs by navArgs()
    private lateinit var binding: BeforeCheckInBinding


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?

    ): View? {
        return inflater.inflate(R.layout.before_check_in, container, false)
    }




    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = BeforeCheckInBinding.bind(view)

        setAllInfo()
        heartButton()
        checkInBtn(view)

    }


    override fun onDestroy() {
        super.onDestroy()
        BeforeCheckInVM.getAndListenCheckedInIdsRegis?.remove()
    }





    private fun setAllInfo() {
        binding.beforeCheckInEventLocationName.text = args.hotSpot.name
        binding.beforeCIAddressTv.text = getAddress()
        binding.beforeCheckInRatingBar.rating = args.hotSpot.rating!!.toFloat()
        binding.beforeCheckInReviews.paintFlags = Paint.UNDERLINE_TEXT_FLAG
        binding.beforeCIDescriptionTv.text = args.hotSpot.description

      //  binding.beforeCheckInReviews.setPaintFlags(binding.beforeCheckInReviews.getPaintFlags() or Paint.UNDERLINE_TEXT_FLAG)
        // binding.beforeCheckInReviews.paintFlags =  Paint.UNDERLINE_TEXT_FLAG or binding.beforeCheckInReviews.paintFlags
        //  binding.beforeCheckInReviews.setPaintFlags(binding.beforeCheckInReviews.getPaintFlags() or Paint.UNDERLINE_TEXT_FLAG)

        updateCheckedInList()
    }


    private fun updateCheckedInList() {
        args.hotSpot.id?.let {
            BeforeCheckInVM.getAndListenCheckedInIdsDB(it) { ids ->
                onCheckedInListChange(ids)
            }
        }


    }




    private fun onCheckedInListChange(checkedIn: ArrayList<CheckedInDB>) {

        binding.beforeCheckInCheckedIn.text = CHECKED_IN + checkedIn.size


       // val toRemove = ArrayList<CheckedInDB>()

/*        args.hotSpot.checkedIn?.forEach {
            if (!checkedInIds.contains(it.id)) {
                toRemove.add(it)
            }
        }

        args.hotSpot.checkedIn?.removeAll(toRemove.toSet())
        val checkedIn = checkedInIds.size
        val pix = binding.beforeCheckInCheckedIn.textSize.toInt()
        val textSizeBefore = (pix/resources.displayMetrics.scaledDensity)

        binding.beforeCheckInCheckedIn.textSize = textSizeBefore + 4
        binding.beforeCheckInCheckedIn.text = CHECKED_IN + checkedIn
        CoroutineScope(IO).launch {
            delay(500)
            CoroutineScope(Main).launch {
                binding.beforeCheckInCheckedIn.textSize = textSizeBefore
            }

        }*/

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
        if (str == "null" || str == null || str == STREET_WITHOUT_NAME) {
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
            val isUserPresent = isUserPresent()
            if (isUserPresent) {
                DataHolder.currentUser?.let { user ->
                    val checkedInDB = CheckedInDB(id = user.uid)
                    UsersAndIds.addUser(user, checkedInDB)
                    BeforeCheckInVM.setCheckedInDB(args.hotSpot, user, null)
                } ?: run {
                    DataHolder.fetchCurrentUserFromDB()
                }
            }

            CoroutineScope(IO).launch {
                val drawable = resources.getDrawable(R.drawable.custom_button_click_effect)
                binding.beforeCheckInCheckInBtn.background = drawable
                delay(100)
                CoroutineScope(Main).launch {
                    val drawable2 = resources.getDrawable(R.drawable.custom_button)
                    binding.beforeCheckInCheckInBtn.background = drawable2
                    if (isUserPresent) {
                        val action = BeforeCheckInDirections.actionBeforeCheckInToAfterCheckIn(args.hotSpot)
                        view.findNavController().navigate(action)
                    }
                }
            }
        }
    }



    private fun isUserPresent(): Boolean {

        val userCurrLocation = MapService.lastLocation.value

        if (userCurrLocation?.longitude == null) {
            Log.i(TAG, "Unable to get user location")
            return false
        }
        val distance = FloatArray(1)
        args.hotSpot.geoPoint?.latitude?.let { lat ->
            args.hotSpot.geoPoint?.longitude?.let { lng ->
               Location.distanceBetween(
                   lat, lng, userCurrLocation.latitude, userCurrLocation.longitude, distance)
            }
        }
        val area = Math.PI * RADIUS.pow(2.0)
        if (distance[0] <= area) {
            return true
        }



        val toastLayout = layoutInflater.inflate(R.layout.custom_toast_layout, custom_toast_layout)
        val toast = Toast(requireContext())
        toast.view = toastLayout
        toast.duration = Toast.LENGTH_LONG
        toast.show()
        return false
    }


}
































