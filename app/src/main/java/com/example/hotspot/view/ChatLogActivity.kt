package com.example.hotspot.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.RecyclerView
import com.example.hotspot.R
import com.example.hotspot.model.ChatMessage
import com.example.hotspot.model.User
import com.example.hotspot.view.NewMessageActivity.Companion.USER_KEY
import com.google.android.material.tabs.TabLayout
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.ChildEventListener
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.ktx.Firebase
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.GroupieViewHolder
import com.xwray.groupie.kotlinandroidextensions.Item
import kotlinx.android.synthetic.main.activity_chat_log.*
import kotlinx.android.synthetic.main.chat_from_row.view.*
import kotlinx.android.synthetic.main.chat_to_row.view.*
import kotlinx.android.synthetic.main.user_row_new_message.view.*

class ChatLogActivity : AppCompatActivity() {

    companion object {
        val TAG = "Chatlog"
    }

    val adapter = GroupAdapter<com.xwray.groupie.GroupieViewHolder>()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat_log)

        recyclerview_chatlog.adapter = adapter

        //Passing an object from one activity to another - in this case we are passing the username
        val user = intent.getParcelableExtra<User>(NewMessageActivity.USER_KEY)
        if (user != null) {
            supportActionBar?.title = user.name
        }

        adapter.add(ChatFromItem("From Message"))
        adapter.add(ChatToItem("To Message"))

        send_button_chatlog.setOnClickListener {
            Log.d(TAG, "Attempt to send message")
//            performSendMessage()
        }


        recyclerview_chatlog.adapter = adapter


    }

    // this is how we actually send a message to firebase
    private fun performSendMessage() {
        val text = editText_chatlog.text.toString()

        val fromId = FirebaseAuth.getInstance().uid
        val user = intent.getParcelableExtra<User>(NewMessageActivity.USER_KEY)
        val toId = user.uid

        if (fromId == null) return

        val reference = FirebaseDatabase.getInstance().getReference("/messages").push()
        val chatMessage = ChatMessage(reference.key!!,text,fromId, toId, System.currentTimeMillis())
        reference.setValue(chatMessage).addOnSuccessListener {
            Log.d(TAG,"Saved our chat message: ${reference.key}")
        }

    }

    private fun listenForMessages() {
        val ref = FirebaseDatabase.getInstance().getReference("/messages")

        ref.addChildEventListener(object: ChildEventListener {
            override fun onChildAdded(snapshot: DataSnapshot, previousChildName: String?) {
                val chatMessage = p0.getValue(ChatMessage::class.java)

                if (chatMessage != null) {
                    Log.d(TAG, chatMessage.text)

                    if (chatMessage.fromId == FirebaseAuth.getInstance().uid) {
                        adapter.add(ChatFromItem(chatMessage.text))
                    } else {
                        adapter.add(ChatToItem(chatMessage.text))
                    }
                }
            }

            override fun onCancelled(error: DatabaseError) {

            }

            override fun onChildChanged(snapshot: DataSnapshot, previousChildName: String?) {

            }

            override fun onChildMoved(snapshot: DataSnapshot, previousChildName: String?) {

            }

            override fun onChildRemoved(snapshot: DataSnapshot) {

            }

        })
    }
}



class ChatFromItem(val text: String): Item() {
    override fun bind(viewHolder: com.xwray.groupie.kotlinandroidextensions.GroupieViewHolder, position: Int) {
        viewHolder.itemView.textview_from_row.text = text
    }

    override fun getLayout(): Int {
        return R.layout.chat_from_row
    }
}

class ChatToItem(val text: String): Item() {
    override fun bind(viewHolder: com.xwray.groupie.kotlinandroidextensions.GroupieViewHolder, position: Int) {
        viewHolder.itemView.textview_to_row.text = text
    }

    override fun getLayout(): Int {
        return R.layout.chat_to_row
    }
}