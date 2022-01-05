package com.example.hotspot.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.hotspot.R
import com.example.hotspot.model.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.*

class chatActivity : AppCompatActivity () {
    var firebaseUser:FirebaseUser? = null
    var reference:DatabaseReference? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.fragment_chat)

        var intent = getIntent()
        var userID = intent.getStringExtra("userID")

        firebaseUser = FirebaseAuth.getInstance().currentUser
        reference = FirebaseDatabase.getInstance().getReference("Users").child(userID!!)

        reference!!.addValueEventListener(object :ValueEventListener() {
            override fun onDataChange(snapshot: DataSnapshot) {
                val user = snapshot.getValue(User::class.java)
                if (currentUser!!.profileImage == ""){
                    .setImageResource(R.drawable.)
                }
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })
    }
}