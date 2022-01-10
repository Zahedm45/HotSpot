package com.example.hotspot.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.RecyclerView
import com.example.hotspot.R
import com.example.hotspot.model.User
import com.example.hotspot.view.NewMessageActivity.Companion.USER_KEY
import com.google.android.material.tabs.TabLayout
import com.google.firebase.database.FirebaseDatabase
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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat_log)

        //Passing an object from one activity to another - in this case we are passing the username
        val user = intent.getParcelableExtra<User>(NewMessageActivity.USER_KEY)
        if (user != null) {
            supportActionBar?.title = user.name
        }

        val adapter = GroupAdapter<com.xwray.groupie.GroupieViewHolder>()
        adapter.add(ChatFromItem("From Message"))
        adapter.add(ChatToItem("To Message"))

        send_button_chatlog.setOnClickListener {
            Log.d(TAG, "Attempt to send message")
//            performSendMessage()
        }


        recyclerview_chatlog.adapter = adapter


    }

/*    class chatMessage(val text: String)

    // this is how we actually send a message to firebase
    private fun performSendMessage(){
        val text = editText_chatlog.text.toString()

        val reference = FirebaseDatabase.getInstance().getReference("/messages").push()
        val chatMessage = chatMessage(text)
        reference.setValue(chatMessage).addOnSuccessListener {
            Log.d(TAG,"Saved our chat message: ${reference.key}")
        }

    }*/
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