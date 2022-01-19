package com.example.hotspot.view

import android.content.Context
import android.graphics.PorterDuff
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
import com.example.hotspot.other.network.TAG
import com.example.hotspot.viewModel.AfterCheckInVM
import com.example.hotspot.viewModel.UsersAndIds
import android.graphics.drawable.Animatable
import android.util.Log
import android.widget.ProgressBar
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import com.example.hotspot.R
import com.example.hotspot.viewModel.BeforeCheckInVM
import com.squareup.picasso.Picasso
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
    private lateinit var progressBar: ProgressBar
    //var isNavigatedFromCheckInBtn = AfterCheckInVM.isNavigatedFromCheckInBtn


/*
    override fun onAttach(context: Context) {
        super.onAttach(context)
        isNavigatedFromCheckInBtn = true
    }
*/




    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_after_check_in, container, false)
    }



    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentAfterCheckInBinding.bind(view)

        if (AfterCheckInVM.isNavigatedFromCheckInBtn) {
            setProgress()
        }


        setHotSpotInfo()
        heartBtn()
        setObserverForCheckedInList()
        isInterestedBtn()




        if (AfterCheckInVM.isNavigatedFromCheckInBtn) {
            CoroutineScope(IO).launch {
                delay(1500)
                var time = (AfterCheckInVM.amountOfUsersToFetch * 800).toLong()
                if (time > 2000) {
                    time = 2000
                }
                Log.i(TAG, "delay time is.. $time")

                delay(time)
                CoroutineScope(Main).launch {
                    AfterCheckInVM.amountOfUsersToFetch = 0
                    clearProgress()
                }
            }
        }
    }



    private fun setProgress() {
        binding.afterCheckInProgressLayout.visibility = View.VISIBLE
       binding.afterCheckedInRecyclerView.isVisible = false
        binding.afterCheckInProgress.visibility = View.VISIBLE
        progressBar = binding.afterCheckInProgress
        progressBar.indeterminateDrawable
            .setColorFilter(ContextCompat.getColor(requireContext(), R.color.orange), PorterDuff.Mode.SRC_IN )
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
                binding.afterCheckedInRecyclerView.isVisible = true


                (done.drawable as Animatable).stop()
                done.visibility = View.GONE
                binding.afterCheckInProgressLayout.visibility = View.GONE


                AfterCheckInVM.isNavigatedFromCheckInBtn = false
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
        val imageview = binding.afterCheckInPartyImg
        val img = BeforeCheckInVM.hotSpotsImg.get(args.hotSpot.id)
        Picasso.get().load(img).into(imageview)
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



    private fun isInterestedBtn() {
        binding.afterCheckInterestedBtn.setOnCheckedChangeListener { _, isChecked ->
            args.hotSpot.id?.let { id ->
                AfterCheckInVM.setIsInterested(isChecked, id)
            }

        }

    }

}


