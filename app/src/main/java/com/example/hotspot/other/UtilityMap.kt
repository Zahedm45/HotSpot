package com.example.hotspot.other

import android.Manifest
import android.content.Context
import android.os.Build
import pub.devrel.easypermissions.EasyPermissions

object UtilityMap {

// https://www.youtube.com/watch?v=6CTIvIAHjrU


    fun hasLocationPermission(context: Context) =

        EasyPermissions.hasPermissions(
            context,
            android.Manifest.permission.ACCESS_FINE_LOCATION,
            android.Manifest.permission.ACCESS_COARSE_LOCATION

        )

}