package com.example.hotspot.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.hotspot.R

class NewMessageActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_message)

        supportActionBar?.title = "Select a Person"

        recyclerview_newMessage

    }
}