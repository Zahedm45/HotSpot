package com.example.hotspot.view

import android.Manifest
import android.content.ContentValues.TAG
import android.content.Intent
import android.content.pm.PackageManager
import androidx.fragment.app.Fragment

import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.core.content.ContextCompat
import com.example.hotspot.R
import com.example.hotspot.other.Constants.PERMISSION_REQUEST_CODE
import com.example.hotspot.other.UtilView.menuOptionClick
import com.example.hotspot.other.UtilityMap
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

import pub.devrel.easypermissions.AppSettingsDialog
import pub.devrel.easypermissions.EasyPermissions

class MapsFragment : Fragment(), EasyPermissions.PermissionCallbacks {

    lateinit var mMap: GoogleMap
//    var fussedLPC: FusedLocationProviderClient? = null
    var location:  LatLng? = null
    var isMakerShowing = false



    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        return inflater.inflate(R.layout.fragment_maps4, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
//        fussedLPC = LocationServices.getFusedLocationProviderClient(requireContext())


        requestLocPermission()


    }




    private val callback = OnMapReadyCallback { googleMap ->

        //val dtu = LatLng(55.784110, 12.517820)
        if (location != null) {
            googleMap.addMarker(MarkerOptions().position(location!!).title("Your current location"))
            googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(location!!, 10f))
            isMakerShowing = true
        }

    }


    private fun requestLocPermission() {


        if(UtilityMap.hasLocationPermission(requireContext())) {

            val la1 =  ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.ACCESS_FINE_LOCATION)
            val la2 = ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.ACCESS_COARSE_LOCATION)

            if(la1 != PackageManager.PERMISSION_GRANTED && la2 != PackageManager.PERMISSION_GRANTED ) {
                // this should not be true
                Log.i(TAG, "Location access is denied")
                return

            }

           val fussedLPC = LocationServices.getFusedLocationProviderClient(requireContext())
            val task = fussedLPC!!.lastLocation
            task.addOnSuccessListener {

                Log.i(TAG, "Here is user location... ${it.latitude} and ${it.longitude}.")

                location = LatLng(it.latitude, it.longitude)

                val mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment?
                mapFragment?.getMapAsync(callback)

            }





        } else {

            EasyPermissions.requestPermissions(
                this,
                "You need to give location permission to run this app.",
                PERMISSION_REQUEST_CODE,
                android.Manifest.permission.ACCESS_COARSE_LOCATION,
                android.Manifest.permission.ACCESS_FINE_LOCATION

            )

        }


    }


    override fun onResume() {
        super.onResume()

        if (!isMakerShowing) {
            requestLocPermission()
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




    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.nav_top_menu, menu)

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        menuOptionClick(item, requireActivity())
        return super.onOptionsItemSelected(item)
    }












}