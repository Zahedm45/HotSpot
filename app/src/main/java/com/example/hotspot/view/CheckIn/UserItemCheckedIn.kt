package com.example.hotspot.view.CheckIn

import android.graphics.Color
import android.view.View
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import androidx.navigation.findNavController
import com.example.hotspot.R
import com.example.hotspot.model.HotSpot
import com.example.hotspot.model.User
import com.example.hotspot.viewModel.UsersAndIds
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.xwray.groupie.kotlinandroidextensions.Item
import kotlinx.android.synthetic.main.after_checked_in_recycler_view_item.view.*

class UserItemCheckedIn(val user: User, val hotSpot: HotSpot, private val viewLifecycleOwner: LifecycleOwner): Item() {

    override fun bind(
        viewHolder: com.xwray.groupie.kotlinandroidextensions.GroupieViewHolder,
        position: Int
    ) {

        val item = viewHolder.itemView



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
        if(user.uid == Firebase.auth.currentUser?.uid){
            item.after_checked_in_person_item_user_name.text ="YOU"
            item.after_checked_in_person_item_user_name.setTextColor(Color.RED)

        }
        item.after_checked_in_person_item_user_pic.setImageBitmap(user.bitmapImg)
        item.after_checked_in_person_item_user_gender.text = "Gender: ${user.gender}"
        item.after_checked_in_person_item_user_age.text = "Age ${user.age}"


        viewHolder.itemView.setOnClickListener {
            val action = FragmentAfterCheckInDirections.actionAfterCheckInToOthersProfile(user)
            it.findNavController().navigate(action)
        }


    }



    override fun getLayout(): Int {
        return R.layout.after_checked_in_recycler_view_item
    }
}


