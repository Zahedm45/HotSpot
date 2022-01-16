package com.example.hotspot.view

import android.content.ContentValues
import android.util.Log
import com.example.hotspot.R
import com.example.hotspot.model.HotSpot
import com.example.hotspot.model.User
import com.xwray.groupie.kotlinandroidextensions.Item
import kotlinx.android.synthetic.main.after_checked_in_recycler_view_item.view.*

class UserItem(val user: User, val hotSpot: HotSpot): Item() {

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


        hotSpot.checkedIn?.forEach {
            if (it.id == user.uid) {

                if (it.isInterested == true) {

                   // item.after_checked_is_interested_img.setImageDrawable(R.drawable.green_light_for_is_interested)

                } else {

                }
            }
        }



    }



    override fun getLayout(): Int {
        return R.layout.after_checked_in_recycler_view_item
    }
}


