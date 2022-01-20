package com.example.hotspot.view.Login

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import com.example.hotspot.R
import com.example.hotspot.databinding.ActivityLoginSuggestionBinding
import com.example.hotspot.other.util.ButtonAnimations
import com.example.hotspot.other.util.DialogWifi
import com.example.hotspot.other.network.ConnectionLiveData
import com.example.hotspot.other.network.TAG
import com.example.hotspot.view.PhoneAuth.ActivityPhoneAuthentification


class ActivityLogin : AppCompatActivity() {

    private lateinit var binding: ActivityLoginSuggestionBinding
    private lateinit var connectionLiveData: ConnectionLiveData



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
       // setContentView(R.layout.activity_login)

        binding = ActivityLoginSuggestionBinding.inflate(layoutInflater)
        setContentView(binding.root)



        supportActionBar?.hide()

        connectionLiveData = ConnectionLiveData(this)
        connectionLiveData.observe(this, { isConnected ->

            binding.activityLoginCreateProfileBtn.setOnClickListener {

                ButtonAnimations.clickButton(binding.activityLoginCreateProfileBtn)
                if(!isConnected) {
                    DialogWifi().show(supportFragmentManager, TAG)
                    return@setOnClickListener
                }
                startPhoneAuthentication()
            }

            binding.activityLoginLoginBtn.setOnClickListener {

                ButtonAnimations.clickButton(binding.activityLoginLoginBtn)
                if(!isConnected) {
                    DialogWifi().show(supportFragmentManager, TAG)
                    return@setOnClickListener
                }
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



