package com.example.hotspot.view.chat

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.hotspot.R
import com.example.hotspot.model.User

import de.hdodenhof.circleimageview.CircleImageView

class ChatUserAdapter(private val context: Context, private val chatUserList: ArrayList<User>) :
    RecyclerView.Adapter<ChatUserAdapter.ViewHolder>() {

    // A ViewHolder describes an item view and metadata about its place within the recyclerview
    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val card: LinearLayout = view.findViewById(R.id.card)
        val profilePic: CircleImageView = view.findViewById(R.id.profile_pic_chat)
        val messageName: TextView = view.findViewById(R.id.message_name)
        val latestReceivedMessage: TextView = view.findViewById((R.id.latest_received_message))
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.fragment_chat_item, parent, false)
        return ViewHolder(view)

    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val user = chatUserList[position]
        holder.messageName.text = user.name
        Glide.with(context).load(user.bitmapImg).into(holder.profilePic)
    }
        override fun getItemCount(): Int {
            return chatUserList.size
        }

    }
