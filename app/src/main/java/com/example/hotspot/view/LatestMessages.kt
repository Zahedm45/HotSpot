package com.example.hotspot.view

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.hotspot.R
import com.example.hotspot.databinding.ActivityLatestMessagesBinding
import com.example.hotspot.model.ChatMessage
import com.example.hotspot.model.User
import com.example.hotspot.view.ChatLog.Companion.TAG
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

class LatestMessages : Fragment() {



    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?) : View? {

        val view = inflater.inflate(R.layout.activity_latest_messages,container, false)
        binding = ActivityLatestMessagesBinding.inflate(inflater, container, false)

        //fetchUsers()
        return view

    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
    }

    override fun onDetach() {
        super.onDetach()
    }




    companion object {
        val USER_KEY = "USER_KEY"
    }

    public fun fetchUsers(){

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
                                Log.d("LatestMessagesActivity", "Listen failed.", e)
                                return@addSnapshotListener
                            }
                            val messages = newDocuments?.toObjects<ChatMessage>()

                            if (messages != null) {
                                tempLatestMessage = ""
                                messages.forEach { message ->

                                    if (message.toFrom == uid+user.uid || message.toFrom == user.uid+uid) {
                                        tempLatestMessage = message.text

                                    }
                                }
                                if (tempLatestMessage != "") {
                                    adapter.add(LatestMessageRow(user, tempLatestMessage))
                                }
                            }
                            else {
                                Log.d(TAG,"Messages == null")
                            }
                        }
                }
                adapter.setOnItemClickListener { item, view ->
                    val latestMessageRow = item as LatestMessageRow
                    val intent = Intent(view.context, ChatLog::class.java)
                    intent.putExtra(NewMessage.USER_KEY, latestMessageRow.user)
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



    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        when (item?.itemId) {
            R.id.menu_new_message -> {
                //Navigate to NewMessage

                findNavController().navigate(R.id.action_latest_to_new)
            }
            R.id.menu_sign_out -> {
                //FirebaseAuth.getInstance().signOut()
                //DELETE THIS BUTTON LATER
            }
        }

        return super.onOptionsItemSelected(item)
    }


}
