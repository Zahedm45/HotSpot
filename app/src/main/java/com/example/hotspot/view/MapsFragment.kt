package com.example.hotspot.view

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.graphics.PorterDuff
import androidx.fragment.app.Fragment
import android.os.Bundle
import android.view.*
import android.widget.ProgressBar
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.lifecycle.Observer
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.example.hotspot.R
import com.example.hotspot.databinding.FragmentMaps4Binding
import com.example.hotspot.model.HotSpot
import com.example.hotspot.other.ButtonAnimations
import com.example.hotspot.other.Constants.ACTION_START_OR_RESUME_SERVICE
import com.example.hotspot.other.MapUtility
import com.example.hotspot.other.UtilView.menuOptionClick
import com.example.hotspot.other.service.MapService
import com.example.hotspot.view.infoWindow.InfoWindow
import com.example.hotspot.viewModel.DataHolder
import com.example.hotspot.viewModel.MapsAndHotspotsVM
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.*
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.firestore.GeoPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


import pub.devrel.easypermissions.AppSettingsDialog
import pub.devrel.easypermissions.EasyPermissions
import android.view.Gravity

import android.widget.FrameLayout
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.LiveData
import com.example.hotspot.view.createProfilePackage.SharedViewModelCreateProfile
import com.example.hotspot.viewModel.BeforeCheckInVM


class MapsFragment : Fragment(), EasyPermissions.PermissionCallbacks {

    private val viewModel = MapsAndHotspotsVM
    private var isMakerShowing = false
    private lateinit var binding: FragmentMaps4Binding
    private var googleMap: GoogleMap? = null
    private var mapFragment: SupportMapFragment? = null
    private lateinit var progressBar: ProgressBar
    private var location: LatLng? = null
    private val markers: ArrayList<Marker> = ArrayList()
    private lateinit var snackbar : Snackbar



    override fun onAttach(context: Context) {
        super.onAttach(context)
        DataHolder.fetchCurrentUserFromDB()
    }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        return inflater.inflate(R.layout.fragment_maps4, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentMaps4Binding.bind(view)

        requestLocPermissionAndTrackLocation()
        addProgressBar()
        myLocationBtn(view)




        viewModel.updateUserIsCheckedIn()
    }



    override fun onDestroyView() {
        super.onDestroyView()
        MapsAndHotspotsVM.showHotSpotReg?.remove()

    }




    @SuppressLint("UseCompatLoadingForDrawables")
    private fun myLocationBtn(view: View) {
        binding.fragmentMapsMyLocationBtn.setOnClickListener {
          //  addHotSpotsInDB()
            if (location != null && googleMap != null) {
                moveCamara(12f)
            }
            ButtonAnimations.clickImageButton(binding.fragmentMapsMyLocationBtn)

        }
    }




    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


/*        if (Firebase.auth.uid == null) {
            Log.i(TAG, "not logged in ..")
            val intent = Intent(requireActivity(), LoginActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
            startActivity(intent)
            requireActivity().finish()
        }*/

        setHasOptionsMenu(true)
    }


    override fun onResume() {
        super.onResume()



        viewModel.updateUserIsCheckedIn()
        googleMap?.let {
            clearProgressBar()
        }

    }

    override fun onStart() {
        super.onStart()
        snackbar = showSnackBarMessage()
        viewModel.getIsUserCheckedIn().observe(this.viewLifecycleOwner){
            if(it){
                snackbar.show()
            }
        }
        viewModel.updateUserIsCheckedIn()
    }

    override fun onPause() {
        super.onPause()
        if(snackbar.isShown){
            snackbar.dismiss()
        }
        viewModel.updateUserIsCheckedIn()
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
                val action = MapsFragmentDirections.actionMapsFragmentToBeforeCheckIn(it)
                view?.findNavController()?.navigate(action)
            }

        }
    }







    private fun addProgressBar() {
        progressBar = binding.fragmentMapsProgressBar
        progressBar.indeterminateDrawable
            .setColorFilter(ContextCompat.getColor(requireContext(), R.color.orange), PorterDuff.Mode.SRC_IN )
//        requireActivity().window.setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
//            WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE)

    }


    private fun clearProgressBar() {
        progressBar.visibility = View.GONE
        binding.fragmentMapsLoadingImg.isVisible = false

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
private fun showSnackBarMessage() : Snackbar {
    val snackbar = Snackbar.make((binding.fragmentMapsMyLocationBtn), "" , Snackbar.LENGTH_INDEFINITE)


    val customSnackView: View = layoutInflater.inflate(R.layout.snackbar_my_hotspot, this.activity?.findViewById<View>(R.id.snackbar_cardView) as? ViewGroup)
    layoutInflater.inflate(R.layout.snackbar_my_hotspot, this.activity?.findViewById<View>(R.id.snackbar_myhotspot_cardView) as? ViewGroup, false)
    val layout = snackbar.view as Snackbar.SnackbarLayout
    layout.setPadding(0,0,0,0)
    customSnackView.setOnClickListener(){
        //TODO fix navigation when clicking on snackbar.
        //findNavController().navigate(R.id.action_mapsFragment_to_afterCheckIn)
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








}