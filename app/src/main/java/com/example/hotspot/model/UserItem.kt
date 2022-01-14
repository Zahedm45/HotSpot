package com.example.hotspot.model

import android.content.ContentValues
import android.util.Log
import com.example.hotspot.R
import com.xwray.groupie.kotlinandroidextensions.Item
import kotlinx.android.synthetic.main.after_checked_in_recycler_view_item.view.*

class UserItem(val user: User): Item() {

    override fun bind(
        viewHolder: com.xwray.groupie.kotlinandroidextensions.GroupieViewHolder,
        position: Int
    ) {

        val item = viewHolder.itemView

        item.after_checked_in_person_item_user_name.text = user.name
        item.after_checked_in_person_item_user_pic.setImageBitmap(user.bitmapImg)
        item.after_checked_in_person_item_user_gender.text = "Gender: ${user.gender}"
        item.after_checked_in_person_item_user_age.text = "Age ${user.age}"


        viewHolder.itemView.setOnClickListener {
            Log.i(ContentValues.TAG, "Click listener $user")
        }
    }



    override fun getLayout(): Int {
        return R.layout.after_checked_in_recycler_view_item
    }
}


