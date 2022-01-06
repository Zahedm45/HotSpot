package com.example.hotspot.other.service
import android.content.ContentValues.TAG
import android.content.Intent
import android.util.Log
import androidx.lifecycle.LifecycleService
import com.example.hotspot.other.Constants.ACTION_PAUSE_SERVICE
import com.example.hotspot.other.Constants.ACTION_START_OR_RESUME_SERVICE
import com.example.hotspot.other.Constants.ACTION_STOPPED_SERVICE

class TrackingService : LifecycleService() {


    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        intent?.let {
            when (it.action) {

                ACTION_START_OR_RESUME_SERVICE -> {
                    Log.i(TAG,"Started or resumed service ")
                }

                ACTION_PAUSE_SERVICE -> {
                    Log.i(TAG,"Pause service ")
                }

                ACTION_STOPPED_SERVICE -> {
                    Log.i(TAG,"Stopped service ")
                }



            }
        }

        return super.onStartCommand(intent, flags, startId)
    }

}