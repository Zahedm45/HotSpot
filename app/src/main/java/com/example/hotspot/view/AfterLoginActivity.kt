package com.example.hotspot.view

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.example.hotspot.R
import com.example.hotspot.other.network.ConnectionLiveData
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.dialog.MaterialAlertDialogBuilder

class AfterLoginActivity: AppCompatActivity() {
    private lateinit var connectionLiveData: ConnectionLiveData

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        val intent = Intent(this, LatestMessagesActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK.or(Intent.FLAG_ACTIVITY_NEW_TASK)
        startActivity(intent)
        /*super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_after_login)

        connectionLiveData = ConnectionLiveData(this)
        connectionLiveData.observe(this, { isConnected ->
            when(isConnected){
                true -> print("is connected to internet")
                false -> print("connection error")
            }
        })

//        navigateToMapFragment(intent)
        supportActionBar?.hide()
        val btn = findViewById<BottomNavigationView>(R.id.bottomNavigationView)
        val navCont = findNavController(R.id.nav_host_fragment)
        btn.setupWithNavController(navCont)
        btn.itemIconTintList = null





        navCont.addOnDestinationChangedListener{_, destination, _ ->
            when(destination.id){
                R.id.chat -> {
                btn.animate().apply {
                    duration = 150
                    this.alpha(0f)
                }.withEndAction { btn.visibility = View.GONE }.start()
                }
                else -> {
                    btn.visibility = View.VISIBLE
                    btn.animate().apply {
                        duration = 150
                        this.alpha(1f)
                    }.start()
                }
            }
        }*/
    }






//    override fun onNewIntent(intent: Intent?) {
//        super.onNewIntent(intent)
//        navigateToMapFragment(intent)
//    }
//
//
//    private fun navigateToMapFragment(intent: Intent?) {
//        if (intent?.action == ACTION_SHOW_TRACKING_FRAGMENT) {
//            // Reconsideration is needed
//            findNavController(R.id.nav_host_fragment).navigate(R.id.trackingFragment)
//        }
//
//    }

}