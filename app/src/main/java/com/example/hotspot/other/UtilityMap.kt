package com.example.hotspot.other

import android.Manifest
import android.content.ContentValues
import android.content.Context
import android.os.Build
import android.util.Log
import pub.devrel.easypermissions.EasyPermissions

object TrackingUtility {

//    fun hasLocationPermission(context: Context) =
//        // devise is not running on android queue
//
//
//        EasyPermissions.hasPermissions(
//            context,
//            Manifest.permission.ACCESS_FINE_LOCATION
        //            ,
//            Manifest.permission.ACCESS_COARSE_LOCATION,
//        )



/*        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.Q) {
            Log.i(ContentValues.TAG, "hellooo1")
            EasyPermissions.hasPermissions(
                context,
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION
            )

        } else {

            Log.i(ContentValues.TAG, "hellooo2")

            EasyPermissions.hasPermissions(
                context,
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION,
                Manifest.permission.ACCESS_BACKGROUND_LOCATION
            )
        }*/






    fun hasLocationPermissions(context: Context) =
        if(Build.VERSION.SDK_INT < Build.VERSION_CODES.Q) {
            EasyPermissions.hasPermissions(
                context,
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION
            )
        } else {
            EasyPermissions.hasPermissions(
                context,
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION,
                Manifest.permission.ACCESS_BACKGROUND_LOCATION
            )
        }




}