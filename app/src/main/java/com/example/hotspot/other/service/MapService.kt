package com.example.hotspot.other.service

import android.annotation.SuppressLint
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.NotificationManager.IMPORTANCE_LOW
import android.app.PendingIntent
import android.app.PendingIntent.FLAG_UPDATE_CURRENT
import android.content.ContentValues.TAG
import android.content.Context
import android.content.Intent
import android.location.Location
import android.os.Build
import android.os.Looper
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat
import androidx.lifecycle.LifecycleService
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.example.hotspot.R
import com.example.hotspot.other.Constants.ACTION_PAUSE_SERVICE
import com.example.hotspot.other.Constants.ACTION_SHOW_TRACKING_FRAGMENT
import com.example.hotspot.other.Constants.ACTION_START_OR_RESUME_SERVICE
import com.example.hotspot.other.Constants.ACTION_STOP_SERVICE
import com.example.hotspot.other.Constants.FASTEST_LOCATION_INTERVAL
import com.example.hotspot.other.Constants.LOCATION_UPDATE_INTERVAL
import com.example.hotspot.other.Constants.NOTIFICATION_CHANNEL_ID
import com.example.hotspot.other.Constants.NOTIFICATION_CHANNEL_NAME
import com.example.hotspot.other.Constants.NOTIFICATION_ID
import com.example.hotspot.other.MapUtility
import com.example.hotspot.view.ActivityLoadingPage
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest.PRIORITY_HIGH_ACCURACY
import com.google.android.gms.location.LocationResult
import com.google.android.gms.maps.model.LatLng



class MapService : LifecycleService() {
    var isFirstRun = true
    private lateinit var fusedLocationProviderClient: FusedLocationProviderClient



    companion object {

        val isTracking = MutableLiveData<Boolean>()
        val lastLocation = MutableLiveData<LatLng>()


    }


    private fun postInitialValues() {
        isTracking.postValue(false)
    }


    @SuppressLint("VisibleForTests")
    override fun onCreate() {
        super.onCreate()
        postInitialValues()
        // maybe need to change
        fusedLocationProviderClient = FusedLocationProviderClient(this)

        isTracking.observe(this, Observer {
            updateLocationTracking(it)

        })
    }



    @SuppressLint("LogNotTimber")
    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        intent?.let {
            when (it.action) {
                ACTION_START_OR_RESUME_SERVICE -> {
                    if(isFirstRun) {
                        startForegroundService()
                        isFirstRun = false
                    } else {
                        Log.i(TAG,"Resuming service...")
                    }
                }
                ACTION_PAUSE_SERVICE -> {
                    Log.i(TAG,"Paused service")
                }
                ACTION_STOP_SERVICE -> {
                    Log.i(TAG,"Stopped service")
                }


                else -> {Log.i(TAG, "Messages is not found")}
            }
        }
        return super.onStartCommand(intent, flags, startId)
    }





    @SuppressLint("MissingPermission")
    private fun updateLocationTracking(isTracking: Boolean) {
        if (isTracking) {

            if (MapUtility.hasLocationPermission(this)) {
                // maybe wrong
                val request = com.google.android.gms.location.LocationRequest().apply {
                    interval = LOCATION_UPDATE_INTERVAL
                    fastestInterval = FASTEST_LOCATION_INTERVAL
                    priority = PRIORITY_HIGH_ACCURACY
                }

                fusedLocationProviderClient.requestLocationUpdates(
                    request,
                    locationCallback,
                    Looper.getMainLooper()
                )

            }


        } else {
            fusedLocationProviderClient.removeLocationUpdates(locationCallback)
        }

    }



    val locationCallback = object : LocationCallback() {
        override fun onLocationResult(result: LocationResult?) {
            super.onLocationResult(result)

            if (isTracking.value!!) {
                result?.locations?.let { locations ->

                    for (location in locations) {
                        addLastLocation(location)
                    }
                }

            }
        }
    }




    private fun addLastLocation(location: Location?) {
        location?.let {
            val position = LatLng(location.latitude, location.longitude)

            if (!lastLocation.equals(position)) {
                lastLocation.postValue(position)
            }
            Log.i(TAG, "New location: ${lastLocation.value?.latitude} and ${lastLocation.value?.longitude}")
        }

    }






    private fun startForegroundService() {

        isTracking.postValue(true)

        val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE)
                as NotificationManager

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            createNotificationChannel(notificationManager)
        }

        val notificationBuilder = NotificationCompat.Builder(this, NOTIFICATION_CHANNEL_ID)
            .setAutoCancel(true)
            .setOngoing(false)
            .setSmallIcon(R.drawable.dancing_pic)
            .setContentTitle("Running App")
            .setContentIntent(getMainActivityPendingIntent())

        startForeground(NOTIFICATION_ID, notificationBuilder.build())
    }





    private fun getMainActivityPendingIntent() = PendingIntent.getActivity(
        this,
        0,
        Intent(this, ActivityLoadingPage::class.java).also {
            it.action = ACTION_SHOW_TRACKING_FRAGMENT
        },
        FLAG_UPDATE_CURRENT
    )





    @RequiresApi(Build.VERSION_CODES.O)
    private fun createNotificationChannel(notificationManager: NotificationManager) {
        val channel = NotificationChannel(
            NOTIFICATION_CHANNEL_ID,
            NOTIFICATION_CHANNEL_NAME,
            IMPORTANCE_LOW
        )
        notificationManager.createNotificationChannel(channel)
    }



}