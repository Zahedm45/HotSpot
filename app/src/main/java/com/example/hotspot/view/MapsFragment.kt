package com.example.hotspot.view

import android.Manifest
import android.os.Build
import androidx.fragment.app.Fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.hotspot.R
import com.example.hotspot.other.Constants.PERMISSION_REQUEST_CODE
import com.example.hotspot.other.UtilityMap

import pub.devrel.easypermissions.AppSettingsDialog
import pub.devrel.easypermissions.EasyPermissions

class MapsFragment : Fragment(), EasyPermissions.PermissionCallbacks {
// https://www.youtube.com/watch?v=6CTIvIAHjrU


/*

    private val callback = OnMapReadyCallback { googleMap ->

        val dtu = LatLng(55.784110, 12.517820)
        googleMap.addMarker(MarkerOptions().position(dtu).title("Marker in DTU"))
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(dtu, 11f))
    }
*/

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
//        val mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment?
//        mapFragment?.getMapAsync(callback)
        requestLocPermission()


    }

    private fun requestLocPermission() {
        if(UtilityMap.hasLocationPermission(requireContext())) {
            return
        }

        EasyPermissions.requestPermissions(
            this,
            "You need to give location permission to run this app.",
            PERMISSION_REQUEST_CODE,
            Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.ACCESS_FINE_LOCATION
        )

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