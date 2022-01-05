package com.example.hotspot.view.chat

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.hotspot.R
import com.example.hotspot.model.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.*

class ChatUserActivity : AppCompatActivity() {
    var chatUserList = ArrayList<User>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.fragment_chat_overview)

        getChatUsersList()
    }

    fun getChatUsersList(){
        val firebase: FirebaseUser = FirebaseAuth.getInstance().currentUser!!
        var databaseReference: DatabaseReference = FirebaseDatabase.getInstance().getReference("Users")

        databaseReference.addValueEventListener(object :ValueEventListener{
            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(applicationContext,error.message,Toast.LENGTH_SHORT).show()
            }

            override fun onDataChange(snapshot: DataSnapshot) {
                chatUserList.clear()
                for (dataSnapShot:DataSnapshot in snapshot.children){
                    val user = dataSnapShot.getValue(User::class.java)

                    if(user!!.emailAddress.equals(firebase.uid)){
                        chatUserList.add(user)
                    }
                }
                val chatUserAdapter = ChatUserAdapter(this@ChatUserActivity,chatUserList)
            }


        })
    }
}