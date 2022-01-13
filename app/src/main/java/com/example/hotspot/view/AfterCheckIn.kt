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
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.GroupieViewHolder
import com.xwray.groupie.kotlinandroidextensions.Item
import kotlinx.android.synthetic.main.after_checked_in_recycler_view_item.view.*

import androidx.navigation.fragment.navArgs
import com.example.hotspot.model.User
import com.example.hotspot.viewModel.DataHolder
import com.example.hotspot.viewModel.PersonalProfileVM


class AfterCheckIn : Fragment() {

    private lateinit var binding: FragmentAfterCheckInBinding
    private val args: AfterCheckInArgs by navArgs()



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_after_check_in, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentAfterCheckInBinding.bind(view)



        setHotSpotInfo()
        heartBtn()



        val adapter = GroupAdapter<GroupieViewHolder>()
/*
        adapter.add(UserItem("User Name1"))
        adapter.add(UserItem("User Name2"))
        adapter.add(UserItem("User Name3"))
        adapter.add(UserItem("User Name"))
        adapter.add(UserItem("User Name"))
        adapter.add(UserItem("User Name"))
        adapter.add(UserItem("User Name"))
        adapter.add(UserItem("User Name"))
        adapter.add(UserItem("User Name"))
        adapter.add(UserItem("User Name"))
        adapter.add(UserItem("User Name"))
        adapter.add(UserItem("User Name"))
        adapter.add(UserItem("User Name"))
        adapter.add(UserItem("User Name"))*/

        val user = User()
        DataHolder.currentUser?.let {
            user.name = it.name
            user.bitmapImg = it.bitmapImg
            user.age = it.age
            user.gender = it.gender

            adapter.add(UserItem(user))
        }





        binding.afterCheckedInRecyclerView.adapter = adapter
        binding.afterCheckedInRecyclerView.suppressLayout(true)


    }




    private fun setHotSpotInfo() {
        binding.afterCheckInHotSpotName.text = args.hotSpot.name
        val checkedInSize = args.hotSpot.checkedIn?.size
        if (checkedInSize != null) {
            binding.afterCheckInCheckedIn.text = checkedInSize.toString()

        } else {
            binding.afterCheckInCheckedIn.text = "0"
        }
    }


    private fun heartBtn() {
        binding.afterCheckInFavoriteBtnWhite.setOnClickListener {
            binding.afterCheckInFavoriteBtnWhite.visibility = View.GONE
            binding.afterCheckInFavoriteBtnThemeColor.visibility = View.VISIBLE

        }

        binding.afterCheckInFavoriteBtnThemeColor.setOnClickListener {
            binding.afterCheckInFavoriteBtnThemeColor.visibility = View.GONE
            binding.afterCheckInFavoriteBtnWhite.visibility = View.VISIBLE

        }
    }






}








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

            Log.i(TAG, "Click listener $user")
        }
    }



    override fun getLayout(): Int {
        return R.layout.after_checked_in_recycler_view_item
    }
}









/*    @SuppressLint("ClickableViewAccessibility")
    private fun disableScrollingUpperPart() {
        val scrollview = binding.afterCheckInScrollView
        scrollview.setOnTouchListener(OnTouchListener { v, event ->
            true
        })
    }*/


/*        binding.afterCheckInScrollView.viewTreeObserver.addOnScrollChangedListener( ViewTreeObserver.OnScrollChangedListener {
            val scX = binding.afterCheckInScrollView.scrollX
            val scY = binding.afterCheckInScrollView.scrollY

            if(scX == 0 && scY >= 655f) {
                disableScrollingUpperPart()
                binding.afterCheckedInRecyclerView.suppressLayout(false)
                Log.i(TAG, "Scroll Scroll x  ${scX} y $scY")
            }

            Log.i(TAG, "Scroll ${scX} y $scY")

        })*/




