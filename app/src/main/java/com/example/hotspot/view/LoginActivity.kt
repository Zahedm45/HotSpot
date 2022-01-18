package com.example.hotspot.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AlertDialog
import androidx.compose.ui.res.stringResource
import androidx.fragment.app.FragmentManager
import com.example.hotspot.R
import com.example.hotspot.databinding.ActivityLoginSuggestionBinding
import com.example.hotspot.other.ButtonAnimations
import com.example.hotspot.other.DialogWifi
import com.example.hotspot.other.network.ConnectionLiveData
import com.example.hotspot.other.network.TAG
import com.example.hotspot.view.phoneAuthentification.ActivityPhoneAuthentification


class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginSuggestionBinding
    private lateinit var connectionLiveData: ConnectionLiveData



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
       // setContentView(R.layout.activity_login)

        binding = ActivityLoginSuggestionBinding.inflate(layoutInflater)
        setContentView(binding.root)
        connectionLiveData = ConnectionLiveData(this)


        supportActionBar?.hide()

        connectionLiveData = ConnectionLiveData(this)
        connectionLiveData.observe(this, { isConnected ->

            binding.activityLoginCreateProfileBtn.setOnClickListener {
                if(!isConnected) {
                    DialogWifi().show(supportFragmentManager, TAG)
                    return@setOnClickListener
                }
                ButtonAnimations.clickButton(binding.activityLoginCreateProfileBtn)
                startPhoneAuthentication()
            }

            binding.activityLoginLoginBtn.setOnClickListener {
                if(!isConnected) {
                    DialogWifi().show(supportFragmentManager, TAG)
                    return@setOnClickListener
                }
                ButtonAnimations.clickButton(binding.activityLoginLoginBtn)
                startPhoneAuthentication()
            }

        })

    }

    private fun startPhoneAuthentication(){
        val intentPhoneAuth = Intent(this, ActivityPhoneAuthentification::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
        startActivity(intentPhoneAuth)
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
    }

    private fun dialogBuiilder(){
        val build = AlertDialog.Builder(this)
        build.setTitle("Internet disconnected.")
        build.setMessage("Please connect to the Internet to continue using Hotspot.")
        build.setIcon(R.drawable.vector_no_wifi_colored)
        build.setNegativeButton("Ok"){ which, hello ->

        }
        val alertDialog: AlertDialog = build.create()
        // Set other dialog properties
        alertDialog.setCancelable(false)
        alertDialog.show()
    }
}



