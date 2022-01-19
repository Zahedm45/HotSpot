package com.example.hotspot.view

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.hotspot.R
import com.example.hotspot.databinding.ActivityChatLogBinding
import com.example.hotspot.model.ChatMessage
import com.example.hotspot.model.User
import com.example.hotspot.view.NewMessage.Companion.USER_KEY
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.*
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.GroupieViewHolder
import com.xwray.groupie.kotlinandroidextensions.Item
import kotlinx.android.synthetic.main.activity_chat_log.*
import kotlinx.android.synthetic.main.chat_from_row.view.*
import kotlinx.android.synthetic.main.chat_to_row.view.*
import kotlinx.android.synthetic.main.user_row_new_message.view.*
import com.squareup.picasso.Picasso

class ChatLog : Fragment() {

    companion object {
        val TAG = "chatlog"
    }

    val adapter = GroupAdapter<GroupieViewHolder>() //vid 6 - 22:15
    val db = Firebase.firestore
    val currentUserId = FirebaseAuth.getInstance().uid
    var latestMessageTimestamp: Long = -1

    private lateinit var binding: ActivityChatLogBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?) : View? {
        val view = inflater.inflate(R.layout.activity_chat_log,container, false)
        binding = ActivityChatLogBinding.inflate(inflater, container, false)

        recyclerview_chatlog.adapter = adapter //vid 6 - 22:15
        //Passing an object from one activity to another - in this case we are passing the username

        listenForMessages()

        send_button_chatlog.setOnClickListener {
            Log.d(TAG, "Attempt to send message")
            performSendMessage()
        }

        return view

    }


    private fun listenForMessages() {

        val fromid = FirebaseAuth.getInstance().uid

        val fragment = User()
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
        recyclerview_chatlog.scrollToPosition(adapter.itemCount -1)

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

                    if (document == newDocuments.elementAt(newDocuments.size() -1)) {
                        latestMessageTimestamp = document.document.data["timestamp"].toString().toLong()
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

        /*val targetImage = viewHolder.itemView.image_from_row
        Picasso.get()
            .load("file:src/main/res/drawable-mdpi/bitmap.bmp")
            .into(targetImage)*/

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