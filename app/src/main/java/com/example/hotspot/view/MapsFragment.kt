package com.example.hotspot.view

import android.annotation.SuppressLint
import android.content.ContentValues.TAG
import android.content.Intent
import androidx.fragment.app.Fragment

import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.example.hotspot.R
import com.example.hotspot.databinding.FragmentMaps4Binding
import com.example.hotspot.model.HotSpot
import com.example.hotspot.other.Constants.ACTION_START_OR_RESUME_SERVICE
import com.example.hotspot.other.MapUtility
import com.example.hotspot.other.UtilView.menuOptionClick
import com.example.hotspot.other.service.MapService
import com.example.hotspot.viewModel.MapsAndHotspotsVM
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.*
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

import pub.devrel.easypermissions.AppSettingsDialog
import pub.devrel.easypermissions.EasyPermissions


class MapsFragment : Fragment(), EasyPermissions.PermissionCallbacks {


    private var isMakerShowing = false
    private lateinit var binding: FragmentMaps4Binding

    private var marker: Marker? = null





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


        binding.fragmentMapsMyLocationBtn.setOnClickListener {

          //  MapsAndHotspotsVM.showHotSpots(binding, mapFragment)

            if (location != null && googleMap != null) {
                moveCamara(12f)

            }

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




    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (Firebase.auth.uid == null) {
            Log.i(TAG, "not logged in ..")

            val intent = Intent(requireActivity(), LoginActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
            startActivity(intent)
            requireActivity().finish()
        }
        setHasOptionsMenu(true)

    }







    private fun sendCommandToService(action: String) =

        Intent(requireContext(), MapService::class.java).also {
            it.action = action
            requireContext().startService(it)
        }



    var location: LatLng? = null

    private fun observeUserPosition() {


        MapService.lastLocation.observe(viewLifecycleOwner, Observer { it ->
            if(it != null) {
//                val i = it.last().last()
//                Log.i(TAG, "location is 1 ${i.latitude} and ${i.longitude}")
                val latitude =   it.latitude
                val longitude =   it.longitude
                location = LatLng(latitude, longitude)

                location?.let {
                    updateBlueDot(it)
                }

            }
        })




//        TrackingService.lastLocation.observe(viewLifecycleOwner, Observer {
//            if(it.last().isNotEmpty()) {
////                val i = it.last().last()
////                Log.i(TAG, "location is 1 ${i.latitude} and ${i.longitude}")
//                val latitude =   it.last().last()?.latitude
//                val longitude =   it.last().last()?.longitude
//                val location = LatLng(latitude, longitude)
//                updateMarker(location)
//            }
//        })


    }





    private var circleAroundPos: Circle? = null
    private var circleAroundPos2: Circle? = null
    private var googleMap: GoogleMap? = null
    private var mapFragment: SupportMapFragment? = null





//    private fun upDateHotSpots() {
//
//        googleMap2?.observe(viewLifecycleOwner, Observer {
//            if (it != null) {
//
//                googleMap2!!.removeObservers(viewLifecycleOwner)
//            }
//
//        })
//
//    }



    // private var markers: ArrayList<Marker> = ArrayList()

    @SuppressLint("MissingPermission")
    private fun updateBlueDot(location: LatLng) {

        //marker?.remove()
        circleAroundPos?.remove()
        circleAroundPos2?.remove()

      //  mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment?

        if (mapFragment == null) {
            mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment?
        }


        mapFragment?.getMapAsync {
         //   marker = it.addMarker(MarkerOptions().position(location).title("Your current location"))
            //it.moveCamera(CameraUpdateFactory.newLatLngZoom(location, 10f))

            googleMap = it



/*            it.setMaxZoomPreference(14.0f)
            it.setMinZoomPreference(6.0f)*/



/*
            circleAroundPos2 = it.addCircle(
                CircleOptions()
                    .center(location)
                    .radius(10.0)
                    .strokeWidth(3f)
                    .strokeColor(Color.BLUE)
                    .fillColor(Color.CYAN) )



            circleAroundPos = it.addCircle(
                CircleOptions()
                    .center(location)
//                    .radius(140.0)
//                    .strokeWidth(3f)
//                    .strokeColor(Color.GREEN)
//                    .fillColor(Color.argb(70, 50, 100, 50))
                    )
*/


            it.isMyLocationEnabled = true
            it.uiSettings.isMyLocationButtonEnabled = false
            if(!isMakerShowing) {
                MapsAndHotspotsVM.showHotSpots({hotSpots -> onSuccess(hotSpots)})
                moveCamara(12f)
                isMakerShowing = true
            }



        }




//        if (markers.isNotEmpty()) {
//            markers.clear()
//        }
//
//        val mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment?
//        mapFragment?.getMapAsync {
//            val marker = it.addMarker(MarkerOptions().position(location).title("Your current location"))
//
//            if (marker != null) {
//                markers.add(marker)
//            }
//            it.moveCamera(CameraUpdateFactory.newLatLngZoom(location, 10f))
//            isMakerShowing = true
//
//        }

    }



    private fun moveCamara(zoom: Float) {
        googleMap?.moveCamera(CameraUpdateFactory.newLatLngZoom(location!!, zoom))
    }








    private fun onSuccess(hotSpots: ArrayList<HotSpot>) {

        hotSpots.forEach { crrHotSpot ->
            val lat = crrHotSpot.address?.latitude
            val lng = crrHotSpot.address?.longitude
            val name = crrHotSpot.hotSpotName.toString()

            if (lat != null && lng != null) {
                val location = LatLng(lat, lng)
                googleMap?.let {
                    it.addMarker(
                        MarkerOptions()
                            .position(location)
                            .title(name)
                    )?.apply {
                        Log.i(TAG, "Tss ${this.id}")
                        this.showInfoWindow()
                    }

                }
            }
        }

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












}