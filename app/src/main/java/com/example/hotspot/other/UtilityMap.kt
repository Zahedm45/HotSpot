package com.example.hotspot.other

import android.content.Context
import pub.devrel.easypermissions.EasyPermissions

object UtilityMap {

    fun hasLocationPermission(context: Context) =

        EasyPermissions.hasPermissions(
            context,
            android.Manifest.permission.ACCESS_FINE_LOCATION,
            android.Manifest.permission.ACCESS_COARSE_LOCATION

        )

}