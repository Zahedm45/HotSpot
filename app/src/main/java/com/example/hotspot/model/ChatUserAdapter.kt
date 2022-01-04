package com.example.hotspot.model

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.hotspot.R

class ChatUserAdapter (private val context: Context, private val userList: ArrayList<User>) :
    RecyclerView.Adapter<ChatUserAdapter.ViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChatUserAdapter.ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.fragment_chat_item, parent,false)
        return ViewHolder(view)

    }

    override fun onBindViewHolder(holder: ChatUserAdapter.ViewHolder, position: Int) {
        val user = userList[position]
        holder.messageName.text = user.name

        Glide.with(context).load(user.bitmapImg).into(holder.profilePic)

        holder.card.setOnClickListener{
            val intent = Intent()
            intent.putExtra("UserId", user.name)
        }
    }

    override fun getItemCount(): Int {
        return userList.size
    }

    // A ViewHolder describes an item view and metadata about its place within the recyclerview
    class ViewHolder(view: View) : RecyclerView.ViewHolder(view){
        val card:LinearLayout = view.findViewById(R.id.card)
        val profilePic:ImageView = view.findViewById(R.id.profile_pic_chat)
        val messageName:TextView = view.findViewById(R.id.message_name)
        val latestReceivedMessage:TextView = view.findViewById((R.id.latest_received_message))
    }
}