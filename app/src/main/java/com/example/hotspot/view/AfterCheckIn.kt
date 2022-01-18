package com.example.hotspot.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import com.example.hotspot.R
import com.example.hotspot.databinding.FragmentAfterCheckInBinding
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.GroupieViewHolder

import androidx.navigation.fragment.navArgs
import com.example.hotspot.model.User
import com.example.hotspot.viewModel.AfterCheckInVM
import com.example.hotspot.viewModel.UsersAndIds


class AfterCheckIn : Fragment() {

    private lateinit var binding: FragmentAfterCheckInBinding
    private val args: AfterCheckInArgs by navArgs()

    private val adapter = GroupAdapter<GroupieViewHolder>()
    lateinit var groupieUserCheckedIns: ArrayList<UserItemCheckedIn>




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
        setObserverForCheckedInList()
        isInterestedBtn()

    }





    private fun setObserverForCheckedInList() {
        val hoSpot = args.hotSpot
        AfterCheckInVM.setListenerToCheckedInListDB(hoSpot)

        UsersAndIds.getUser().observe(viewLifecycleOwner, Observer { it ->

            if (it != null) {
                groupieUserCheckedIns = ArrayList()
                for (user in it) {
                    groupieUserCheckedIns.add(UserItemCheckedIn(user, hoSpot, viewLifecycleOwner))

                }

                //adapter.clear()
                adapter.update(groupieUserCheckedIns)

                binding.afterCheckedInRecyclerView.adapter = adapter
                setCheckedInUI(it.size)
            }
        })
    }



    private fun setHotSpotInfo() {
        binding.afterCheckInHotSpotName.text = args.hotSpot.name
    }


    private fun setCheckedInUI(checkedInSize: Int) {
        binding.afterCheckInCheckedIn.text = checkedInSize.toString()
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
/*        val item = UserItem(user)

        if (!adapterHelper.contains(user)) {
            adapter.add(item)
        }*/

    }

    override fun onStop() {
        super.onStop()
        AfterCheckInVM.checkedInListenerRig?.remove()
    }





    private fun isInterestedBtn() {
        binding.afterCheckInterestedBtn.setOnCheckedChangeListener { _, isChecked ->
            args.hotSpot.id?.let { id ->
                AfterCheckInVM.setIsInterested(isChecked, id)
            }

        }

    }

}







/*     fun onSuccess() {
     AfterCheckInVM.checkedInUsersAndIds.users.observe(viewLifecycleOwner, Observer { it ->
          // Log.i(TAG, "AFTERCHEC")

           if (it != null) {
               Log.i(TAG, "AFTERCHEC2 ${it}")
               val groupieUsers = ArrayList<UserItem>()



*//*                it.forEach { user ->
                    Log.i(TAG, "AFTERCHEC3 ")
                    groupieUsers.add(UserItem(user))
                }*//*

                for (i in it) {
                    groupieUsers.add(UserItem(i))
                    adapter.add(UserItem(i))
                    adapter.notifyDataSetChanged()
                }


               // adapter.update(groupieUsers)
            }


        })

    }*/






