package com.example.hotspot.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.hotspot.R
import com.example.hotspot.databinding.FragmentAfterCheckInBinding
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.GroupieViewHolder

import androidx.navigation.fragment.navArgs
import com.example.hotspot.model.User
import com.example.hotspot.viewModel.AfterCheckInVM
import com.example.hotspot.viewModel.DataHolder


class AfterCheckIn : Fragment() {

    private lateinit var binding: FragmentAfterCheckInBinding
    private val args: AfterCheckInArgs by navArgs()

    val adapter = GroupAdapter<GroupieViewHolder>()
    val adapterHelper = ArrayList<User>()




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





        DataHolder.currentUser?.let {
            adapter.add(UserItem(it))
            adapterHelper.add(it)
        }

        val hoSpot = args.hotSpot


//        hoSpot.checkedIn?.let {
//            AfterCheckInVM.getCheckedInUserFromDB(it, { user -> onSuccess(user)})
//        }



        binding.afterCheckedInRecyclerView.adapter = adapter
        binding.afterCheckedInRecyclerView.suppressLayout(true)


        AfterCheckInVM.setListenerToCheckedInListDB(hoSpot)

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


    fun onSuccess(user: User) {
        val item = UserItem(user)

        if (!adapterHelper.contains(user)) {
            adapter.add(item)
        }


    }





}




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









