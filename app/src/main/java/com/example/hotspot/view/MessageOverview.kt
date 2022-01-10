package com.example.hotspot.view

import android.os.Bundle
import android.view.*
import android.widget.ImageView
import androidx.fragment.app.Fragment
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.example.hotspot.R
import com.example.hotspot.model.User
import com.example.hotspot.other.UtilView
import com.example.hotspot.repository.Repository
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.Item
import kotlinx.android.synthetic.main.chat_item.view.*
import kotlinx.android.synthetic.main.fragment_message_overview.*


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

        //RVmessage_overview.adapter = GroupAdapter<ViewHolder>()
        Repository.fetchMessages()
        view.findViewById<CardView>(R.id.chatOnClick).setOnClickListener(this)

    }


    override fun onClick(chatsView: View?){
        when(chatsView!!.id){
            R.id.chatOnClick -> navController.navigate(R.id.action_messageOverview_to_chat)
        }
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)

    }



    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.nav_top_menu, menu)

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        UtilView.menuOptionClick(item, requireActivity())
        return super.onOptionsItemSelected(item)
    }
}
