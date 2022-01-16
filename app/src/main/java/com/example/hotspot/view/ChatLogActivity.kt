package com.example.hotspot.view

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.example.hotspot.R
import com.example.hotspot.model.ChatMessage
import com.example.hotspot.model.User
import com.example.hotspot.view.NewMessageActivity.Companion.USER_KEY
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.firestore.*
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.GroupieViewHolder
import com.xwray.groupie.kotlinandroidextensions.Item
import kotlinx.android.synthetic.main.activity_chat_log.*
import kotlinx.android.synthetic.main.chat_from_row.view.*
import kotlinx.android.synthetic.main.chat_to_row.view.*
import kotlinx.android.synthetic.main.user_row_new_message.view.*
import com.squareup.picasso.Picasso

class ChatLogActivity : AppCompatActivity() {

    companion object {
        val TAG = "chatlog"
    }

    val adapter = GroupAdapter<GroupieViewHolder>() //vid 6 - 22:15
    val db = Firebase.firestore
    val currentUserId = FirebaseAuth.getInstance().uid
    var latestMessageTimestamp: Long = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat_log)
        Log.d(TAG, "Activity is shown on screen")
        recyclerview_chatlog.adapter = adapter //vid 6 - 22:15

        //Passing an object from one activity to another - in this case we are passing the username
        val user = intent.getParcelableExtra<User>(USER_KEY)
        if (user != null) {
            supportActionBar?.title = user.name
        }


        listenForMessages()

        send_button_chatlog.setOnClickListener {
            Log.d(TAG, "Attempt to send message")
            performSendMessage()
        }
    }


    private fun listenForMessages() {

        val fromid = FirebaseAuth.getInstance().uid
        val user = intent.getParcelableExtra<User>(USER_KEY)
        val toid = user?.uid
        val ref = db.collection("messages")

            ref.whereIn("toFrom",listOf(fromid+toid,toid+fromid))
                .whereGreaterThan("timestamp",latestMessageTimestamp)
                .addSnapshotListener {
                newDocuments, e ->
            if (e != null) {
                Log.w(TAG, "Listen failed.", e)
                return@addSnapshotListener
            }
                    Log.w(TAG, "listenForMessages")
                    if (newDocuments != null) {
                        newMessageAdded(newDocuments)
                    }
        }
    }

    private fun newMessageAdded(newDocuments: QuerySnapshot) {
        val fromid = FirebaseAuth.getInstance().uid
        val user = intent.getParcelableExtra<User>(USER_KEY)
        val toid = user?.uid

        for (document in newDocuments.documentChanges) {
            when (document.type) {
                DocumentChange.Type.ADDED -> {
                    if (document.document.data["fromId"] == fromid) {
                        adapter.add(ChatToItem(
                            document.document.data["text"].toString(),
                            fromid!!
                        ))

                    } else { adapter.add(ChatFromItem(
                        document.document.data["text"].toString(),toid!!

                    )) }

                    Log.d(TAG, "${document.document.id} => ${document.document.data}")

                    if (document == newDocuments.elementAt(newDocuments.size() -1)) {
                        latestMessageTimestamp = document.document.data["timestamp"].toString().toLong()
                        Log.d(TAG,"New latestMessageTimestamp = $latestMessageTimestamp")
                    }
                }

                DocumentChange.Type.MODIFIED -> Log.d(TAG,"Document modified. Possible error!")
                DocumentChange.Type.REMOVED -> Log.d(TAG,"Document removed. Possible error!")
            }

        }
        recyclerview_chatlog.scrollToPosition(adapter.itemCount -1)
    }

    // this is how we actually send a message to firebase
    private fun performSendMessage() {

        val db = Firebase.firestore

        val user = intent.getParcelableExtra<User>(USER_KEY)
        val text = editText_chatlog.text.toString()
        val fromid = FirebaseAuth.getInstance().uid
        val toid = user?.uid
        val timestamp = System.currentTimeMillis()

        val message = ChatMessage(toid+fromid, text, fromid,
            toid, timestamp)

        db.collection("messages").document(timestamp.toString()).set(message)
            //.add(message)
            .addOnSuccessListener {
                Log.d(TAG, "Message sent")
            }
            .addOnFailureListener {
            Log.w(TAG, "Error sending message")
            }

        editText_chatlog.text.clear()

    }
}



class ChatFromItem(val text: String, val toId: String): Item() {
    override fun bind(viewHolder: com.xwray.groupie.kotlinandroidextensions.GroupieViewHolder, position: Int) {
        viewHolder.itemView.textview_from_row.text = text
        val ref = "https://firebasestorage.googleapis.com/v0/b/hotspot-onmyown.appspot.com" +
                "/o/images%2F" + toId + "?alt=media&token="


        val targetImage = viewHolder.itemView.image_from_row
        Picasso.get()
            .load(ref)
            .into(targetImage)



    }

    override fun getLayout(): Int {
        return R.layout.chat_from_row
    }
}

class ChatToItem(val text: String, val fromId: String): Item() {
    override fun bind(viewHolder: com.xwray.groupie.kotlinandroidextensions.GroupieViewHolder, position: Int) {
        viewHolder.itemView.textview_to_row.text = text
        val ref = "https://firebasestorage.googleapis.com/v0/b/hotspot-onmyown.appspot.com" +
                "/o/images%2F" + fromId + "?alt=media&token="

        val targetImage = viewHolder.itemView.image_to_row
        Picasso.get()
            .load(ref)
            .into(targetImage)


    }

    override fun getLayout(): Int {
        return R.layout.chat_to_row
    }
}