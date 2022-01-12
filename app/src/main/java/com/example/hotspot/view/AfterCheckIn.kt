package com.example.hotspot.view

import android.content.ContentValues.TAG
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.hotspot.R
import com.example.hotspot.databinding.FragmentAfterCheckInBinding
import com.example.hotspot.model.User
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.GroupieViewHolder
import com.xwray.groupie.kotlinandroidextensions.Item
import kotlinx.android.synthetic.main.after_checked_in_recycler_view_item.view.*

class AfterCheckIn : Fragment() {

    private lateinit var binding: FragmentAfterCheckInBinding


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_after_check_in, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentAfterCheckInBinding.bind(view)


        binding.afterCheckInFavoriteBtnWhite.setOnClickListener {
            binding.afterCheckInFavoriteBtnWhite.visibility = View.GONE
            binding.afterCheckInFavoriteBtnThemeColor.visibility = View.VISIBLE

        }


        binding.afterCheckInFavoriteBtnThemeColor.setOnClickListener {
            binding.afterCheckInFavoriteBtnThemeColor.visibility = View.GONE
            binding.afterCheckInFavoriteBtnWhite.visibility = View.VISIBLE

        }

        val adapter = GroupAdapter<GroupieViewHolder>()

        adapter.add(UserItem("user"))

        binding.afterCheckedInRecyclerView.adapter = adapter







    }

}





class UserItem(val user: String): Item() {

    override fun bind(
        viewHolder: com.xwray.groupie.kotlinandroidextensions.GroupieViewHolder,
        position: Int
    ) {
        Log.i(TAG, "User is called..")

        viewHolder.itemView.after_checked_in_person_item_user_name.text = user
    }



    override fun getLayout(): Int {
        return R.layout.after_checked_in_recycler_view_item
    }
}

