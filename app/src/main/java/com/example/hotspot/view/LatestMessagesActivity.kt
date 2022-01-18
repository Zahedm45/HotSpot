package com.example.hotspot.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import com.example.hotspot.R
import com.example.hotspot.databinding.ActivityLatestMessagesBinding
import com.example.hotspot.model.User
import com.example.hotspot.view.createProfilePackage.ActivityCreateProfile
import com.google.firebase.auth.FirebaseAuth
import com.xwray.groupie.GroupAdapter
import kotlinx.android.synthetic.main.activity_latest_messages.*

import com.google.firebase.firestore.*
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObjects
import com.google.firebase.ktx.Firebase
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.chat_to_row.view.*
import kotlinx.android.synthetic.main.latest_message_row.*
import kotlinx.android.synthetic.main.latest_message_row.view.*
import kotlinx.android.synthetic.main.user_row_new_message.view.*

private lateinit var binding: ActivityLatestMessagesBinding

class LatestMessagesActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLatestMessagesBinding.inflate(layoutInflater)
        setContentView(binding.root)
        fetchUsers()

//        verifyUserIsLoggedIn()

    }


    companion object {
        val USER_KEY = "USER_KEY"
    }

    private fun fetchUsers(){

        val db = Firebase.firestore
        val userRef = db.collection("users")
        val messageRef = db.collection("messages")

        //val user = intent.getParcelableExtra<User>(NewMessageActivity.USER_KEY)
        val uid = FirebaseAuth.getInstance().uid

        userRef.get()
            .addOnSuccessListener {
                val users = it.toObjects<User>()
                val adapter = GroupAdapter<com.xwray.groupie.GroupieViewHolder>()
                var tempLatestMessage = ""
                users.forEach { user ->
                    messageRef.whereIn("toFrom",listOf(uid+user.uid,user.uid+uid))
                        .addSnapshotListener {
                                newDocuments, e ->
                            if (e != null) {
                                Log.w("LatestMessagesActivity", "Listen failed.", e)
                                return@addSnapshotListener
                            }

                            if (newDocuments != null) {
                                for (document in newDocuments) {
                                    if (document.data["toFrom"] == uid+user.uid) {
                                        tempLatestMessage = document.data["text"].toString()
                                    }

                                }
                                if (tempLatestMessage == "") {
                                    for (document in newDocuments) {
                                        if (document.data["toFrom"] == user.uid+uid) {
                                            tempLatestMessage = document.data["text"].toString()
                                        }
                                    }
                                }
                            }
                        }
                    adapter.add(LatestMessageRow(user, tempLatestMessage))
                }
                adapter.setOnItemClickListener { item, view ->
                    val latestMessageRow = item as LatestMessageRow
                    val intent = Intent(view.context, ChatLogActivity::class.java)
                    intent.putExtra(NewMessageActivity.USER_KEY, latestMessageRow.user)
                    startActivity(intent)
                }
                binding.recyclerviewLatestMessages.adapter = adapter
            }
    }

    class LatestMessageRow(val user: User, val latestMessage: String): com.xwray.groupie.kotlinandroidextensions.Item() {
        override fun bind(
            viewHolder: com.xwray.groupie.kotlinandroidextensions.GroupieViewHolder,
            position: Int
        ) {
            viewHolder.itemView.latest_message_name.text = user.name
            viewHolder.itemView.latest_message_received.text = latestMessage

            val imageId = user.uid
            val ref = "https://firebasestorage.googleapis.com/v0/b/hotspot-onmyown.appspot.com" +
                    "/o/images%2F" + imageId + "?alt=media&token="
            val targetImage = viewHolder.itemView.latest_message_pic
            Picasso.get()
                .load(ref)
                .into(targetImage)
        }

        override fun getLayout(): Int {
            return R.layout.latest_message_row
        }
    }



    private fun verifyUserIsLoggedIn() {
        //Perform check to see if user is logged in (if necessary)
        val uid = FirebaseAuth.getInstance().uid
        if (uid == null) {
            val intent = Intent(this, ActivityCreateProfile::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK.or(Intent.FLAG_ACTIVITY_NEW_TASK)
            startActivity(intent)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.nav_top_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        when (item?.itemId) {
            R.id.menu_new_message -> {
                val intent = Intent(this, NewMessageActivity::class.java)
                //intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK.or(Intent.FLAG_ACTIVITY_NEW_TASK)
                startActivity(intent)
            }
            R.id.menu_sign_out -> {
                FirebaseAuth.getInstance().signOut()
                //Run registration activity
                val intent = Intent(this, ActivityCreateProfile::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK.or(Intent.FLAG_ACTIVITY_NEW_TASK)
                startActivity(intent)
            }
        }

        return super.onOptionsItemSelected(item)
    }
}
