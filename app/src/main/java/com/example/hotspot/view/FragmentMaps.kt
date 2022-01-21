package com.example.hotspot.view

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.PorterDuff
import androidx.fragment.app.Fragment
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.ProgressBar
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.navigation.findNavController
import com.example.hotspot.R
import com.example.hotspot.databinding.FragmentMaps4Binding
import com.example.hotspot.model.HotSpot
import com.example.hotspot.other.util.ButtonAnimations
import com.example.hotspot.other.util.Constants.ACTION_START_OR_RESUME_SERVICE
import com.example.hotspot.other.util.MapUtility
import com.example.hotspot.other.util.UtilView.menuOptionClick
import com.example.hotspot.other.service.MapService
import com.example.hotspot.view.infoWindow.InfoWindow
import com.example.hotspot.viewModel.DataHolder
import com.example.hotspot.viewModel.MapsAndHotspotsVM
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.*
import com.google.firebase.firestore.GeoPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


import pub.devrel.easypermissions.AppSettingsDialog
import pub.devrel.easypermissions.EasyPermissions

import com.example.hotspot.other.network.TAG


class FragmentMaps : Fragment(), EasyPermissions.PermissionCallbacks {

    private var isMakerShowing = false
    private lateinit var binding: FragmentMaps4Binding
    private var googleMap: GoogleMap? = null
    private var mapFragment: SupportMapFragment? = null
    private lateinit var progressBar: ProgressBar
    private var location: LatLng? = null
    private val markers: ArrayList<Marker> = ArrayList()
    var isSnackBarShowing = false
    lateinit var view2: View




    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        return inflater.inflate(R.layout.fragment_maps4, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        view2 = view
        binding = FragmentMaps4Binding.bind(view)


        requestLocPermissionAndTrackLocation()

        if (MapsAndHotspotsVM.isAppJustOpened) {
            addProgressBar()

        } else {
            logicsForCheckedInlayout()
            isSnackBarShowing = true

        }

        myLocationBtn(view)

        DataHolder.fetchCurrentUserFromDB()

    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }


    override fun onPause() {
        super.onPause()
        clearCheckedIn()
    }

    override fun onResume() {
        super.onResume()
        googleMap?.let {
            clearProgressBar()
        }
        DataHolder.fetchCurrentUserFromDB()
    }




    override fun onDestroyView() {
        super.onDestroyView()
        MapsAndHotspotsVM.showHotSpotReg?.remove()

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






    @SuppressLint("UseCompatLoadingForDrawables")
    private fun myLocationBtn(view: View) {
        binding.fragmentMapsMyLocationBtn.setOnClickListener {

            if (location != null && googleMap != null) {
                moveCamara(12f)
            }
            ButtonAnimations.clickImageButton(binding.fragmentMapsMyLocationBtn)

        }
    }




    private fun requestLocPermissionAndTrackLocation() {

        if(MapUtility.hasLocationPermission(requireContext())) {
            sendCommandToService(ACTION_START_OR_RESUME_SERVICE)
            observeUserPosition()

        } else {
            MapUtility.requestPermission(this)
        }
    }




    override fun onPermissionsGranted(requestCode: Int, perms: MutableList<String>) {
        requestLocPermissionAndTrackLocation()
    }

    override fun onPermissionsDenied(requestCode: Int, perms: MutableList<String>) {

        if(EasyPermissions.somePermissionPermanentlyDenied(this, perms)) {
            AppSettingsDialog.Builder(this).build().show()
        } else {
            requestLocPermissionAndTrackLocation()
        }
    }



    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this)
    }






    private fun sendCommandToService(action: String): Intent  {
        return Intent(requireContext(), MapService::class.java).also {
            it.action = action
            requireContext().startService(it)
        }
    }




    private fun observeUserPosition() {
        MapService.lastLocation.observe(viewLifecycleOwner, Observer { it ->
            if(it != null) {
                val latitude =   it.latitude
                val longitude =   it.longitude
                location = LatLng(latitude, longitude)

                location?.let {
                    updateBlueDot()
                }
            }
        })
    }





    @SuppressLint("MissingPermission")
    private fun updateBlueDot() {

        if (mapFragment == null) {
            mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment?
        }

        mapFragment?.getMapAsync {
            googleMap = it

            it.isMyLocationEnabled = true
            it.uiSettings.isMyLocationButtonEnabled = false
            if(!isMakerShowing) {
                MapsAndHotspotsVM.showHotSpots { hotSpots -> onSuccess(hotSpots) }
                moveCamara(12f)
                isMakerShowing = true
            }
        }
    }





    private fun onSuccess(hotSpots: ArrayList<HotSpot>) {
        MapsAndHotspotsVM.hotSpots = hotSpots

        if (markers.isNotEmpty()) {
            markers.forEach {
                it.remove()
            }
        }

        hotSpots.forEach { crrHotSpot ->
            val lat = crrHotSpot.geoPoint?.latitude
            val lng = crrHotSpot.geoPoint?.longitude
            val name = crrHotSpot.name.toString()
            val rating = crrHotSpot.rating

            if (lat != null && lng != null) {
                val location = LatLng(lat, lng)
                googleMap?.let {

                    it.addMarker(
                        MarkerOptions()
                            .position(location)
                            .title(name)
                            .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_ROSE))
                            .snippet("Rating: $rating")

                    )?.apply {
                        //this.showInfoWindow()
                        markers.add(this)
                    }

                    it.setInfoWindowAdapter(InfoWindow(requireContext()))

                }
            }
        }

        setOnClickListener(hotSpots)
        CoroutineScope(IO).launch {
            delay(1000)
            CoroutineScope(Main).launch {
                clearProgressBar()
            }
        }


    }





    private fun setOnClickListener(hotSpotsArr: ArrayList<HotSpot>) {

        googleMap?.setOnInfoWindowClickListener { marker ->
            var hotSpot: HotSpot? = null
            val address = GeoPoint(marker.position.latitude, marker.position.longitude)

// Since it is not possible to break a forEach loop in Kotlin, we need to add another nesting lambda
            run loop@{
                hotSpotsArr.forEach {
                    if (it.name == marker.title && it.geoPoint == address) {
                        hotSpot = it
                        return@loop
                    }
                }
            }


            hotSpot?.let {
                val action = FragmentMapsDirections.actionMapsFragmentToBeforeCheckIn(it)
                view?.findNavController()?.navigate(action)
            }

        }
    }







    private fun addProgressBar() {
        binding.fragmentMapsLoadingImg.visibility = View.VISIBLE
        progressBar = binding.fragmentMapsProgressBar
        progressBar.visibility = View.VISIBLE
        progressBar.indeterminateDrawable
            .setColorFilter(ContextCompat.getColor(requireContext(), R.color.orange), PorterDuff.Mode.SRC_IN )
//        requireActivity().window.setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
//            WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE)

    }


    private fun clearProgressBar() {

        if (MapsAndHotspotsVM.isAppJustOpened) {
            progressBar.visibility = View.GONE
            binding.fragmentMapsLoadingImg.visibility = View.GONE
            MapsAndHotspotsVM.isAppJustOpened = false
        }

        if (!isSnackBarShowing) {
            logicsForCheckedInlayout()
        }

       // requireActivity().window.clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE)

    }



    private fun moveCamara(zoom: Float) {
        googleMap?.moveCamera(CameraUpdateFactory.newLatLngZoom(location!!, zoom))
    }



    // implementations of top menu
    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.nav_top_menu, menu)

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        menuOptionClick(item, requireActivity())
        return super.onOptionsItemSelected(item)
    }




    private fun showCheckedIn() {
        binding.mapsFragmentMyHotspotBtnLayout.visibility = View.VISIBLE
        binding.mapsGoToMyHotspotBtn.setOnClickListener {
            ButtonAnimations.clickButton(binding.mapsGoToMyHotspotBtn)
            navigateToAfterCheckIn(it)
        }

    }

    private fun clearCheckedIn() {
        binding.mapsFragmentMyHotspotBtnLayout.visibility = View.GONE
    }



    private fun navigateToAfterCheckIn(view: View) {

        DataHolder.getCurrentUserHotspot().value?.let { hotSpot ->
            val action = FragmentMapsDirections.actionMapsFragmentToAfterCheckIn(hotSpot)
            Log.i(TAG, "you clicked me..inside ${action}")
            view.findNavController().navigate(action)
        }

    }




















/*    private fun logicsForSnackBar() {
        DataHolder.getCurrentUser().observe(viewLifecycleOwner, Observer {
            it.isUserCheckedIn?.let {
                if (it != "null") {
                    showSnackBar()

                } else {
                    clearSnackBar()
                }
            }
        })

    }*/

/*

    private fun showSnackBar() {
        if (MapsAndHotspotsVM.snackbar == null) {
            MapsAndHotspotsVM.snackbar = showSnackBarMessage()
            MapsAndHotspotsVM.snackbar?.show()

        } else {
            MapsAndHotspotsVM.snackbar?.show()
        }
    }



    private fun clearSnackBar() {

        if (MapsAndHotspotsVM.snackbar != null) {

            if (MapsAndHotspotsVM.snackbar!!.isShown) {
                MapsAndHotspotsVM.snackbar?.dismiss()
            }
        }
    }
*/





   /*

    private fun showSnackBarMessage(): Snackbar {
        val snackbar =
            Snackbar.make((binding.fragmentMapsMyLocationBtn), "", Snackbar.LENGTH_INDEFINITE)


        val customSnackView: View = layoutInflater.inflate(
            R.layout.snackbar_my_hotspot,
            this.activity?.findViewById<View>(R.id.snackbar_cardView) as? ViewGroup
        )
        layoutInflater.inflate(
            R.layout.snackbar_my_hotspot,
            this.activity?.findViewById<View>(R.id.snackbar_myhotspot_cardView) as? ViewGroup,
            false
        )
        val layout = snackbar.view as Snackbar.SnackbarLayout
        layout.setPadding(0, 0, 0, 0)

        customSnackView.setOnClickListener() {


            Log.i(TAG, "you clicked me...${ DataHolder.getCurrentUserHotspot().value}")
            DataHolder.getCurrentUserHotspot().value?.let { hotSpot ->


                val action = MapsFragmentDirections.actionMapsFragmentToAfterCheckIn(hotSpot)
                Log.i(TAG, "you clicked me..inside ${action}")

                view?.findNavController()?.navigate(action)

            }

        }
        val color: Int = resources.getColor(R.color.transparent)
        layout.setBackgroundColor(color)
        layout.addView(customSnackView, 0)

        val view = snackbar.view
        val params = view.layoutParams as CoordinatorLayout.LayoutParams
        params.gravity = Gravity.TOP
        view.layoutParams = params


        return snackbar
    }
*/

}










/*
    private fun addHotSpotsInDB() {
        SubClassForHotspot.defineRH("No Stress Bar", 55.667, 12.5842, requireContext())
        SubClassForHotspot.defineRH("Muck Bar", 55.7143, 12.5595, requireContext())
        SubClassForHotspot.defineRH("Sjus Bar", 55.6699,12.5350, requireContext())
        SubClassForHotspot.defineRH("Cucaracha Bar", 55.67594, 12.566846, requireContext())

        SubClassForHotspot.defineRH("Jaded Stout Bar", 55.6870, 12.5286, requireContext())
        SubClassForHotspot.defineRH("Rusty Outlaw Bar", 55.7001, 12.5326, requireContext())
        SubClassForHotspot.defineRH("Wandering Monk Ale House", 55.7089, 12.5660, requireContext())
        SubClassForHotspot.defineRH("Secret Lady Bar and Grill", 55.6853, 12.5854, requireContext())

    }

*/

