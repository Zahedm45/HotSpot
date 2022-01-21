package com.example.hotspot.view

import android.app.AlertDialog
import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.util.AttributeSet
import android.util.Log
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import androidx.compose.ui.res.colorResource
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.example.hotspot.R
import com.example.hotspot.databinding.ActivityAfterLoginBinding
import com.example.hotspot.databinding.FragmentCreateProfileAgeBinding
import com.example.hotspot.other.network.ConnectionLiveData
import com.example.hotspot.other.network.ConnectivityManager
import com.example.hotspot.viewModel.DataHolder
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.snackbar.Snackbar.SnackbarLayout


class ActivityAfterLogin: AppCompatActivity() {
    private lateinit var connectionLiveData: ConnectionLiveData

    private var _binding: ActivityAfterLoginBinding? = null
    private val binding get() = _binding!!

    private lateinit var snackbar : Snackbar




    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        DataHolder.fetchCurrentUserFromDB()




        setContentView(R.layout.activity_after_login)
        val snackbar = showSnackBarMessage()

        connectionLiveData = ConnectionLiveData(this)
        connectionLiveData.observe(this, { isConnected ->
            when(isConnected){
                true -> if(snackbar.isShown) snackbar.dismiss()
                false -> snackbar.show()
            }
        })

//        navigateToMapFragment(intent)
        supportActionBar?.hide()
        val btn = findViewById<BottomNavigationView>(R.id.bottomNavigationView)
        val navCont = findNavController(R.id.nav_host_fragment)
        btn.setupWithNavController(navCont)
        btn.itemIconTintList = null

        val connectivityManager = ConnectivityManager(this.application)
        connectivityManager.registerConnectionObserver(this)








    }

    override fun onResume() {
        super.onResume()
        val snackbar = showSnackBarMessage()
        connectionLiveData = ConnectionLiveData(this)
        connectionLiveData.observe(this, { isConnected ->
            when(isConnected){
                true -> if(snackbar.isShown) snackbar.dismiss()
                false -> snackbar.show()
            }
        })
    }



    private fun showSnackBarMessage() : Snackbar  {
        val snackbar = Snackbar.make(findViewById(R.id.nav_host_fragment), "" ,Snackbar.LENGTH_INDEFINITE)


        val customSnackView: View = layoutInflater.inflate(R.layout.snackbar_no_wifi, findViewById<View>(R.id.snackbar_cardView) as? ViewGroup)
        val layout = snackbar.view as SnackbarLayout
        layout.setPadding(0,0,0,0)
        customSnackView.setOnClickListener(){
            if(snackbar.isShown) snackbar.dismiss()
        }
        val color: Int = resources.getColor(R.color.transparent)
        layout.setBackgroundColor(color)
        layout.addView(customSnackView, 0)

        return snackbar
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