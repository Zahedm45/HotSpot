package com.example.hotspot.view

import android.content.ContentValues.TAG
import android.content.Intent
import androidx.fragment.app.Fragment

import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.lifecycle.Observer
import com.example.hotspot.R
import com.example.hotspot.databinding.FragmentMaps4Binding
import com.example.hotspot.other.Constants.ACTION_START_OR_RESUME_SERVICE
import com.example.hotspot.other.TrackingUtility
import com.example.hotspot.other.UtilView.menuOptionClick
import com.example.hotspot.other.service.TrackingService
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

import pub.devrel.easypermissions.AppSettingsDialog
import pub.devrel.easypermissions.EasyPermissions

//https://www.youtube.com/watch?v=JpVBPKf2mIU&list=PLQkwcJG4YTCQ6emtoqSZS2FVwZR9FT3BV&index=12


class MapsFragment : Fragment(), EasyPermissions.PermissionCallbacks {


    var isMakerShowing = false
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

        requestLocPermission()
    }




    private fun requestLocPermission() {

        if(TrackingUtility.hasLocationPermission(requireContext())) {
            sendCommandToService(ACTION_START_OR_RESUME_SERVICE)
            observeUserPosition()

        } else {

            TrackingUtility.requestPermission(this)
        }

    }


    override fun onResume() {
        super.onResume()
     //   requestLocPermission()
    }





    override fun onPermissionsGranted(requestCode: Int, perms: MutableList<String>) {

    }

    override fun onPermissionsDenied(requestCode: Int, perms: MutableList<String>) {

        if(EasyPermissions.somePermissionPermanentlyDenied(this, perms)) {
            AppSettingsDialog.Builder(this).build().show()
        } else {
            requestLocPermission()
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

        Intent(requireContext(), TrackingService::class.java).also {
            it.action = action
            requireContext().startService(it)
        }





    private fun observeUserPosition() {


        TrackingService.lastLocation.observe(viewLifecycleOwner, Observer {
            if(it != null) {
//                val i = it.last().last()
//                Log.i(TAG, "location is 1 ${i.latitude} and ${i.longitude}")
                val latitude =   it.latitude
                val longitude =   it.longitude
                val location = LatLng(latitude, longitude)
                updateMarker(location)
            }
        })




//        TrackingService.pathPoints.observe(viewLifecycleOwner, Observer {
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



    private var markers: ArrayList<Marker> = ArrayList()

    private fun updateMarker(location: LatLng) {



        marker?.remove()

        val mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment?
        mapFragment?.getMapAsync {
            marker = it.addMarker(MarkerOptions().position(location).title("Your current location"))
            it.moveCamera(CameraUpdateFactory.newLatLngZoom(location, 10f))
            isMakerShowing = true

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