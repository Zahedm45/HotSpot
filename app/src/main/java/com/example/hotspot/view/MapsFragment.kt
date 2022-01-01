package com.example.hotspot.view

import android.Manifest
import android.annotation.SuppressLint
import android.content.ContentValues.TAG
import android.content.pm.PackageManager
import androidx.fragment.app.Fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import com.example.hotspot.R
import com.example.hotspot.other.Constants.PERMISSION_REQUEST_CODE
import com.example.hotspot.other.UtilityMap
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions

import pub.devrel.easypermissions.AppSettingsDialog
import pub.devrel.easypermissions.EasyPermissions

class MapsFragment : Fragment(), EasyPermissions.PermissionCallbacks {
// https://www.youtube.com/watch?v=6CTIvIAHjrU

    lateinit var mMap: GoogleMap
    var fussedLPC: FusedLocationProviderClient? = null


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_maps4, container, false)



        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        fussedLPC = LocationServices.getFusedLocationProviderClient(requireContext())


        requestLocPermission()


    }


    var location:  LatLng? = null



    private val callback = OnMapReadyCallback { googleMap ->

        //val dtu = LatLng(55.784110, 12.517820)

        if (location != null) {
            googleMap.addMarker(MarkerOptions().position(location!!).title("Marker in DTU"))
            googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(location!!, 10f))
        }

    }



    private fun requestLocPermission() {










        if(UtilityMap.hasLocationPermission(requireContext())) {


            Log.i(TAG, "You got it...")



            val i =  ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.ACCESS_FINE_LOCATION)
            val i2 = ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.ACCESS_COARSE_LOCATION)

            if(i == PackageManager.PERMISSION_GRANTED && i2 == PackageManager.PERMISSION_GRANTED ) {



                val task = fussedLPC!!.lastLocation
                task.addOnSuccessListener {

                    Log.i(TAG, "You got it... ${it.latitude} and ${it.longitude}.")

                    location = LatLng(it.latitude, it.longitude)

                    val mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment?
                    mapFragment?.getMapAsync(callback)

                }

            }





        } else {

            EasyPermissions.requestPermissions(
                this,
                "You need to give location permission to run this app.",
                PERMISSION_REQUEST_CODE,
                Manifest.permission.ACCESS_COARSE_LOCATION,
                Manifest.permission.ACCESS_FINE_LOCATION
            )
        }


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













}