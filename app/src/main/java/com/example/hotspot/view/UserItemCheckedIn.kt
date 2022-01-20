package com.example.hotspot.view

import android.content.ContentValues
import android.util.Log
import android.view.View
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import androidx.navigation.findNavController
import com.example.hotspot.R
import com.example.hotspot.model.HotSpot
import com.example.hotspot.model.User
import com.example.hotspot.viewModel.UsersAndIds
import com.xwray.groupie.kotlinandroidextensions.Item
import kotlinx.android.synthetic.main.after_checked_in_recycler_view_item.view.*

class UserItemCheckedIn(val user: User, val hotSpot: HotSpot, private val viewLifecycleOwner: LifecycleOwner): Item() {

    override fun bind(
        viewHolder: com.xwray.groupie.kotlinandroidextensions.GroupieViewHolder,
        position: Int
    ) {

        val item = viewHolder.itemView


/*        hotSpot.checkedIn?.forEach {
            if (it.id == user.uid) {
                Log.i(ContentValues.TAG, "recycler view ${it.id} and ${user.uid}")

                Log.i(ContentValues.TAG, "recycler view2 ${it.isInterested}")
                if (it.isInterested == true) {

                    Log.i(ContentValues.TAG, "recycler view2")

                    item.interested_img_green.visibility = View.VISIBLE
                    item.interested_img_red.visibility = View.GONE
                    // notifyChanged()


                } else if (it.isInterested != true) {
                    Log.i(ContentValues.TAG, "recycler view3")
                    item.interested_img_red.visibility = View.VISIBLE

                    item.interested_img_green.visibility = View.GONE

                    //notifyChanged()
                } else {
                    item.interested_img_red.visibility = View.GONE
                    item.interested_img_green.visibility = View.GONE


                }
            }
        }*/






        UsersAndIds.getIsInterestedTrueList().observe(viewLifecycleOwner, Observer{ interestedList ->

            if (interestedList.contains(user.uid)) {
                item.interested_img_green.visibility = View.VISIBLE
                item.interested_img_red.visibility = View.GONE

            }  else {
                item.interested_img_red.visibility = View.VISIBLE
                item.interested_img_green.visibility = View.GONE
            }
        })



        item.after_checked_in_person_item_user_name.text = user.name
        item.after_checked_in_person_item_user_pic.setImageBitmap(user.bitmapImg)
        item.after_checked_in_person_item_user_gender.text = "Gender: ${user.gender}"
        item.after_checked_in_person_item_user_age.text = "Age ${user.age}"

/*
        viewHolder.itemView.setOnClickListener {
            Log.i(ContentValues.TAG, "Click listener $user")
        }

*/


        viewHolder.itemView.setOnClickListener {
            val action = AfterCheckInDirections.actionAfterCheckInToOthersProfile(user)
            it.findNavController().navigate(action)
        }


    }



    override fun getLayout(): Int {
        return R.layout.after_checked_in_recycler_view_item
    }
}


