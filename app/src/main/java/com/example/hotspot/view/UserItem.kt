package com.example.hotspot.view

import com.example.hotspot.R
import com.example.hotspot.model.User
import com.xwray.groupie.Item
import com.xwray.groupie.ViewHolder
import kotlinx.android.synthetic.main.chat_item.view.*

class UserItem(val user: User): Item<ViewHolder>() {
    override fun bind(viewHolder: ViewHolder, position: Int) {
        viewHolder.itemView.user_name.text = user.name

    }

    override fun getLayout() = R.layout.fragment_message_overview

}