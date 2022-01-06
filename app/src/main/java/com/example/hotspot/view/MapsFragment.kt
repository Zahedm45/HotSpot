package com.example.hotspot.view

import android.Manifest
import android.content.ContentValues.TAG
import android.content.Intent
import android.os.Build
import androidx.fragment.app.Fragment

import android.os.Bundle
import android.util.Log
import android.view.*
import com.example.hotspot.R
import com.example.hotspot.databinding.FragmentMaps4Binding
import com.example.hotspot.other.Constants.ACTION_START_OR_RESUME_SERVICE
import com.example.hotspot.other.Constants.REQUEST_CODE_LOCATION_PERMISSION
import com.example.hotspot.other.TrackingUtility
import com.example.hotspot.other.UtilView.menuOptionClick
import com.example.hotspot.other.service.Polyline
import com.example.hotspot.other.service.TrackingService
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

import pub.devrel.easypermissions.AppSettingsDialog
import pub.devrel.easypermissions.EasyPermissions

//https://www.youtube.com/watch?v=JpVBPKf2mIU&list=PLQkwcJG4YTCQ6emtoqSZS2FVwZR9FT3BV&index=12


class MapsFragment : Fragment(), EasyPermissions.PermissionCallbacks {

    var location:  LatLng? = null
    var isMakerShowing = false
    private lateinit var binding: FragmentMaps4Binding

    private var isTracking = false
    private var pathPoints = mutableListOf<Polyline>()



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

        binding.foregroundOnlyLocationButton.setOnClickListener {
            sendCommandToService(ACTION_START_OR_RESUME_SERVICE)

        }




    }









    private fun requestLocPermission() {

        if(TrackingUtility.hasLocationPermissions(requireContext())) {
            return
        }
        if(Build.VERSION.SDK_INT < Build.VERSION_CODES.Q) {
            EasyPermissions.requestPermissions(
                this,
                "You need to accept location permissions to use this app.",
                REQUEST_CODE_LOCATION_PERMISSION,
                Manifest.permission.ACCESS_COARSE_LOCATION,
                Manifest.permission.ACCESS_FINE_LOCATION
            )
        } else {
            EasyPermissions.requestPermissions(
                this,
                "You need to accept location permissions to use this app.",
                REQUEST_CODE_LOCATION_PERMISSION,
                Manifest.permission.ACCESS_COARSE_LOCATION,
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_BACKGROUND_LOCATION
            )
        }






//        if(UtilityMap.hasLocationPermission(requireContext())) {
//
//            val la1 =  ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.ACCESS_FINE_LOCATION)
//            val la2 = ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.ACCESS_COARSE_LOCATION)
//
//            if(la1 != PackageManager.PERMISSION_GRANTED && la2 != PackageManager.PERMISSION_GRANTED ) {
//                // this should not be true
//                Log.i(TAG, "Location access is denied")
//                return
//
//            }
//
//           val fussedLPC = LocationServices.getFusedLocationProviderClient(requireContext())
//            val task = fussedLPC!!.lastLocation
//            task.addOnSuccessListener {
//
//                //Log.i(TAG, "Here is user location... ${it.latitude} and ${it.longitude}.")
//
//                //location = LatLng(it.latitude, it.longitude)
//
//                val mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment?
//                mapFragment?.getMapAsync(callback)
//
//            }
//
//
//
//
//
//        } else {
//
//
//
//            EasyPermissions.requestPermissions(
//                this,
//                "You need to give location permission to run this app.",
//                PERMISSION_REQUEST_CODE,
//                Manifest.permission.ACCESS_COARSE_LOCATION,
//                Manifest.permission.ACCESS_FINE_LOCATION,
//                Manifest.permission.ACCESS_BACKGROUND_LOCATION
//
//            )
//
//        }


    }


    override fun onResume() {
        super.onResume()

//        if (!isMakerShowing) {
//            requestLocPermission()
//        }
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






    private fun updateMarker() {
        val mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment?
        mapFragment?.getMapAsync(callback)

    }





    private val callback = OnMapReadyCallback { googleMap ->

        //val dtu = LatLng(55.784110, 12.517820)
        if (location != null) {
            googleMap.addMarker(MarkerOptions().position(location!!).title("Your current location"))
            googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(location!!, 10f))
            isMakerShowing = true
        }

    }


    // implementation of top menu

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.nav_top_menu, menu)

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        menuOptionClick(item, requireActivity())
        return super.onOptionsItemSelected(item)
    }












}