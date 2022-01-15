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
import com.example.hotspot.viewModel.BeforeCheckInVM
import com.example.hotspot.viewModel.DataHolder
import com.example.hotspot.viewModel.UsersAndIds


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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

/*        DataHolder.currentUser?.let {
            UsersAndIds.addUser(it)
            it.uid?.let { userId ->
                BeforeCheckInVM.setCheckedInDB(args.hotSpot, userId, null)
            }
        }*/

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentAfterCheckInBinding.bind(view)

        setHotSpotInfo()
        heartBtn()

/*
        DataHolder.currentUser?.let {
            adapter.add(UserItem(it))
            adapterHelper.add(it)
        }
*/
       // binding.afterCheckedInRecyclerView.suppressLayout(true)

        setObserverForCheckedInList()


    }

    private fun setObserverForCheckedInList() {
        val hoSpot = args.hotSpot
        AfterCheckInVM.setListenerToCheckedInListDB(hoSpot)


        UsersAndIds.getUser().observe(viewLifecycleOwner, Observer { it ->
            if (it != null) {
                val groupieUsers = ArrayList<UserItem>()
                for (i in it) {
                    groupieUsers.add(UserItem(i))

                }

                adapter.clear()
                adapter.addAll(groupieUsers)

                binding.afterCheckedInRecyclerView.adapter = adapter
                setCheckedInUI(it.size)
            }
        })
    }



    private fun setHotSpotInfo() {
        binding.afterCheckInHotSpotName.text = args.hotSpot.name
    }


    private fun setCheckedInUI(checkedInSize: Int) {

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

    override fun onStop() {
        super.onStop()
        AfterCheckInVM.checkedInListenerRig?.remove()
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






