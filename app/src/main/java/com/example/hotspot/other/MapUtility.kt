package com.example.hotspot.other

import android.Manifest
import android.content.ContentValues
import android.content.Context
import android.os.Build
import android.util.Log
import androidx.fragment.app.Fragment
import pub.devrel.easypermissions.EasyPermissions

object MapUtility {

    fun hasLocationPermission(context: Context) =
        EasyPermissions.hasPermissions(
            context,
            Manifest.permission.ACCESS_FINE_LOCATION,
 //           Manifest.permission.ACCESS_COARSE_LOCATION
        )


    fun requestPermission(fragment: Fragment) {
        EasyPermissions.requestPermissions(
            fragment,
            "You need to accept location permission to find HotSpots",
            Constants.REQUEST_CODE_LOCATION_PERMISSION,
 //           Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.ACCESS_FINE_LOCATION
        )
    }

}