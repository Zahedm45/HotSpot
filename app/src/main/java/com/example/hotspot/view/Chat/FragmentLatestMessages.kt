package com.example.hotspot.view.Chat

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.hotspot.R
import com.example.hotspot.databinding.FragmentLatestMessagesBinding
import com.example.hotspot.model.ChatMessage
import com.example.hotspot.model.User
import com.example.hotspot.other.util.ButtonAnimations
import com.example.hotspot.view.Chat.ChatLog.Companion.TAG
import com.google.firebase.auth.FirebaseAuth
import com.xwray.groupie.GroupAdapter

import com.google.firebase.firestore.*
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObjects
import com.google.firebase.ktx.Firebase
import com.squareup.picasso.Picasso
import com.xwray.groupie.GroupieViewHolder
import kotlinx.android.synthetic.main.chat_to_row.view.*
import kotlinx.android.synthetic.main.fragment_latest_messages.*
import kotlinx.android.synthetic.main.latest_message_row.*
import kotlinx.android.synthetic.main.latest_message_row.view.*
import kotlinx.android.synthetic.main.user_row_new_message.view.*



class FragmentLatestMessages : Fragment() {


    private lateinit var binding: FragmentLatestMessagesBinding


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?) : View? {

        val view = inflater.inflate(R.layout.fragment_latest_messages,container, false)
        binding = FragmentLatestMessagesBinding.bind(view)
        setHasOptionsMenu(true)
        Log.d("LatestMessages","onCreate")



        return view

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        fetchUsers()
        binding.ivChat.setOnClickListener(){
            ButtonAnimations.clickButton(binding.ivChat)
            findNavController().navigate(R.id.action_latest_to_new)
        }
        //supportActionBar?.hide()
    }





    companion object {
        val USER_KEY = "USER_KEY"
    }

    @SuppressLint("LogNotTimber")
    public fun fetchUsers(){
        val adapter = GroupAdapter<GroupieViewHolder>()
        val db = Firebase.firestore
        val userRef = db.collection("users")
        val messageRef = db.collection("messages")

        //val user = intent.getParcelableExtra<User>(NewMessageActivity.USER_KEY)
        val uid = FirebaseAuth.getInstance().uid

        userRef.get()
            .addOnSuccessListener {
                val users = it.toObjects<User>()
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
                    val thing = item as LatestMessageRow
                    val toid = thing.user.uid
                    Log.d("ClickPerson",toid!!)
                    Log.d("ClickPerson",view.toString())

                    val action = FragmentLatestMessagesDirections.actionLatestToChatlog(toid,thing.user.name!!)
                    findNavController().navigate(action)
                }
                binding.recyclerviewLatestMessages.adapter = adapter
            }
    }

    class LatestMessageRow(val user: User, val latestMessage: String): com.xwray.groupie.kotlinandroidextensions.Item() {
        val username = user.name
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
        fun getName(): String {
            return username!!
        }
    }
}
