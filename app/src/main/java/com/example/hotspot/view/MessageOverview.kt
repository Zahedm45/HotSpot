package com.example.hotspot.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.example.hotspot.R


class MessageOverview : Fragment(), View.OnClickListener {

    lateinit var navController : NavController

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.fragment_message_overview, container, false)
        (activity as AppCompatActivity?)!!.supportActionBar!!.title = "Messages"
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = Navigation.findNavController(view)
      view.findViewById<CardView>(R.id.chatOnClick).setOnClickListener(this)

    }

    override fun onClick(chatsView: View?){
        when(chatsView!!.id){
            R.id.chatOnClick -> navController.navigate(R.id.action_messageOverview_to_chat)
        }
    }
}