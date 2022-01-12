package com.example.hotspot.view

import android.annotation.SuppressLint
import android.content.ContentValues.TAG
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewTreeObserver
import com.example.hotspot.R
import com.example.hotspot.databinding.FragmentAfterCheckInBinding
import com.example.hotspot.model.User
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.GroupieViewHolder
import com.xwray.groupie.kotlinandroidextensions.Item
import kotlinx.android.synthetic.main.after_checked_in_recycler_view_item.view.*
import android.widget.Toast

import android.view.MotionEvent
import android.view.View.OnTouchListener


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
        adapter.add(UserItem("user"))
        adapter.add(UserItem("user"))
        adapter.add(UserItem("user"))
        adapter.add(UserItem("user"))
        adapter.add(UserItem("user"))
        adapter.add(UserItem("user"))
        adapter.add(UserItem("user"))
        adapter.add(UserItem("user"))
        adapter.add(UserItem("user"))
        adapter.add(UserItem("user"))
        adapter.add(UserItem("user"))
        adapter.add(UserItem("user"))
        adapter.add(UserItem("user"))


        binding.afterCheckedInRecyclerView.adapter = adapter
        binding.afterCheckedInRecyclerView.suppressLayout(true)
       // binding.afterCheckInScrollView.canScrollVertically(0)








        binding.afterCheckInScrollView.viewTreeObserver.addOnScrollChangedListener( ViewTreeObserver.OnScrollChangedListener {
            val scX = binding.afterCheckInScrollView.scrollX
            val scY = binding.afterCheckInScrollView.scrollY

            if(scX == 0 && scY >= 655f) {
                disableScrollingUpperPart()
                binding.afterCheckedInRecyclerView.suppressLayout(false)
                Log.i(TAG, "Scroll Scroll x  ${scX} y $scY")
            }

            Log.i(TAG, "Scroll ${scX} y $scY")

        })






    }



    @SuppressLint("ClickableViewAccessibility")
    private fun disableScrollingUpperPart() {
        val scrollview = binding.afterCheckInScrollView
        scrollview.setOnTouchListener(OnTouchListener { v, event ->
            true
        })
    }

}





class UserItem(val user: String): Item() {

    override fun bind(
        viewHolder: com.xwray.groupie.kotlinandroidextensions.GroupieViewHolder,
        position: Int
    ) {

        viewHolder.itemView.after_checked_in_person_item_user_name.text = user

/*        viewHolder.itemView.after_checked_in_person_item_user_name.text = user
        viewHolder.itemView.after_checked_in_person_item_user_age.text = "33"*/
       // viewHolder.itemView.after_checked_in_person_item_user_pic.setImageResource(R.drawable.persons2)
    }



    override fun getLayout(): Int {
        return R.layout.after_checked_in_recycler_view_item
    }
}








