package com.example.hotspot.view.CheckIn

import android.annotation.SuppressLint
import android.graphics.Paint
import android.location.Location
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.lifecycle.Observer
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.hotspot.R
import com.example.hotspot.databinding.BeforeCheckInBinding
import com.example.hotspot.model.CheckedInDB
import com.example.hotspot.other.util.ButtonAnimations
import com.example.hotspot.other.network.TAG
import com.example.hotspot.other.service.MapService
import com.example.hotspot.view.CheckIn.Constant.CHECKED_IN
import com.example.hotspot.view.CheckIn.Constant.RADIUS
import com.example.hotspot.view.CheckIn.Constant.STREET_WITHOUT_NAME
import com.example.hotspot.viewModel.AfterCheckInVM
import com.example.hotspot.viewModel.BeforeCheckInVM
import com.example.hotspot.viewModel.DataHolder
import com.example.hotspot.viewModel.UsersAndIds
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.custom_toast_layout.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

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
        logicsForCheckedInlayout()

        binding.beforeCheckInReviews.setOnClickListener {
            findNavController().navigate(R.id.Action_beforeCheckin_to_reviews)
        }

    }


    override fun onDestroy() {
        super.onDestroy()
        BeforeCheckInVM.getAndListenCheckedInIdsRegis?.remove()
        clearCheckedIn()
    }

    override fun onResume() {
        super.onResume()
        logicsForCheckedInlayout()
    }



    private fun setAllInfo() {
        binding.beforeCheckInEventLocationName.text = args.hotSpot.name
        binding.beforeCIAddressTv.text = getAddress()
        binding.beforeCheckInRatingBar.rating = args.hotSpot.rating!!.toFloat()
        binding.beforeCheckInReviews.paintFlags = Paint.UNDERLINE_TEXT_FLAG
        binding.beforeCIDescriptionTv.text = args.hotSpot.description



        val imageView = binding.beforeCheckInPartyImg

        val img = BeforeCheckInVM.hotSpotsImg.get(args.hotSpot.id)

        if (img == null) {
            val imageRef = Firebase.storage.reference.child("HotSpots/${args.hotSpot.id}.png")
            imageRef.downloadUrl.addOnSuccessListener { Uri ->
                val imageUrl = Uri.toString()
                Picasso.get().load(imageUrl).into(imageView)
                BeforeCheckInVM.hotSpotsImg.put(args.hotSpot.id!!, imageUrl)
            }

        } else {
            Picasso.get().load(img).into(imageView)
        }





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
            addFavoriteHotSpot()
            binding.beforeCheckInFavoriteBtnWhite.visibility = View.GONE
            binding.beforeCheckInFavoriteBtnThemeColor.visibility = View.VISIBLE
            Log.i(TAG, "Added hot 2")
        }

        binding.beforeCheckInFavoriteBtnThemeColor.setOnClickListener {
            deleteFavoriteHotspot()
            binding.beforeCheckInFavoriteBtnThemeColor.visibility = View.GONE
            binding.beforeCheckInFavoriteBtnWhite.visibility = View.VISIBLE
            Log.i(TAG, "Removed hot 3")

        }
    }


    @SuppressLint("SetTextI18n", "UseCompatLoadingForDrawables")
    private fun checkInBtn(view: View) {

        binding.beforeCheckInCheckInBtn.setOnClickListener {
            val isUserPresent = isUserPresent()
           // val isUserPresent = true
            if (isUserPresent) {
                DataHolder.getCurrentUser().value?.let { user ->
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
                        AfterCheckInVM.isNavigatedFromCheckInBtn = true
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
        val area = Math.PI * RADIUS * RADIUS
        if (distance[0] <= area) {
            return true
        }

        val toastLayout = layoutInflater.inflate(R.layout.custom_toast_layout, custom_toast_layout)
        val textView = toastLayout.findViewById<TextView>(R.id.custom_toast_text_tv)


        val dist = Math.round( (distance[0] / 1000) * 10.0) / 10.0
        textView.text = "You are approximately $dist km away from the location."
        val toast = Toast(requireContext())
        toast.view = toastLayout
        toast.duration = Toast.LENGTH_LONG
        toast.setGravity(Gravity.CENTER_HORIZONTAL or Gravity.TOP, 0, 200)
        toast.show()
        addDelay(toast, 1000)
        return false
    }

    private fun addDelay(toast: Toast, timeMilles: Long) {
        CoroutineScope(IO).launch {
            delay(timeMilles)
            CoroutineScope(Main).launch {
                Log.i(TAG, "delay..")
                toast.cancel()
            }
        }
    }



    private fun addFavoriteHotSpot() {
        args.hotSpot.id?.let { hotSpotId ->

            DataHolder.getCurrentUser().value?.uid?.let { userId ->
                BeforeCheckInVM.addHotSpotDB(hotSpotId, userId)

            } ?: run { Log.i(TAG, "User id is null ($this)") }


        } ?: run { Log.i(TAG, "HotSpot id is null ($this)") }

    }

    private fun deleteFavoriteHotspot(){
        args.hotSpot.id?.let{ hotSpotId ->

            DataHolder.getCurrentUser().value?.uid?.let { userId ->
                BeforeCheckInVM.deleteHotSpotDB(hotSpotId,userId)
            } ?: run { Log.i(TAG, "User id is null ($this)") }

        } ?: run { Log.i(TAG, "HotSpot id is null ($this)") }

    }





    private fun logicsForCheckedInlayout() {
        DataHolder.getCurrentUser().observe(viewLifecycleOwner, Observer {
            it.isUserCheckedIn?.let {
                if (it != "null") {
                    showCheckedIn()

                } else {
                    clearCheckedIn()
                }
            }
        })

    }




    private fun showCheckedIn() {
        binding.beforeCheckInCheckInBtn.isVisible = false
        binding.beforeCheckeInMyHotspotBtnLayout.visibility = View.VISIBLE
        binding.beforeCheckInGoToMyHotspotBtn.setOnClickListener {
            ButtonAnimations.clickButton( binding.beforeCheckInGoToMyHotspotBtn)

            navigateToAfterCheckIn(it)
        }

    }

    private fun clearCheckedIn() {
        binding.beforeCheckeInMyHotspotBtnLayout.visibility = View.GONE
        binding.beforeCheckInCheckInBtn.isVisible = true

    }



    private fun navigateToAfterCheckIn(view: View) {

        DataHolder.getCurrentUserHotspot().value?.let { hotSpot ->
            val action = BeforeCheckInDirections.actionBeforeCheckInToAfterCheckIn(hotSpot)
            Log.i(TAG, "you clicked me..inside ${action}")
            view.findNavController().navigate(action)
        }

    }



}

















