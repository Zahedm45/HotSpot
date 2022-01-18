package com.example.hotspot.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import com.example.hotspot.databinding.FragmentAfterCheckInBinding
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.GroupieViewHolder

import androidx.navigation.fragment.navArgs
import com.example.hotspot.viewModel.AfterCheckInVM
import com.example.hotspot.viewModel.UsersAndIds
import android.graphics.drawable.Animatable
import androidx.core.view.isVisible
import com.example.hotspot.R
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


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


        binding.afterCheckedInRecyclerView.visibility = View.GONE
        setHotSpotInfo()
        heartBtn()
        setObserverForCheckedInList()
        isInterestedBtn()





        CoroutineScope(IO).launch {
            delay(4000)
            CoroutineScope(Main).launch {
                clearProgress()
            }
        }




    }




    private fun clearProgress() {

        val done = binding.afterCheckInProgressDone
        done.visibility = View.VISIBLE
        (done.drawable as Animatable).start()

        CoroutineScope(IO).launch {
            delay(800)
            CoroutineScope(Main).launch {
                binding.afterCheckInBlank.isVisible = false
                binding.afterCheckInProgress.visibility = View.GONE
                binding.afterCheckedInRecyclerView.visibility = View.VISIBLE
                (done.drawable as Animatable).stop()
                done.visibility = View.GONE

            }
        }

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





/*    override fun onDestroyView() {
        super.onDestroyView()
        AfterCheckInVM.checkedInListenerRig?.remove()
        Log.i(TAG, "On destroy view")
    }*/





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






