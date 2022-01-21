package com.example.hotspot.view.Chat

import android.content.Intent
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.navigation.NavController


class FragmentMessageOverview : Fragment(), View.OnClickListener {

    lateinit var navController : NavController

/*
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        return inflater.inflate(R.layout.fragment_message_overview, container, false)
        //(activity as AppCompatActivity?)!!.supportActionBar!!.title = "Messages"
        //return view

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = Navigation.findNavController(view)
      view.findViewById<CardView>(R.id.chatOnClick).setOnClickListener(this)

    }
*/
    override fun onClick(chatsView: View?){
        /*when(chatsView!!.id){
            R.id.chatOnClick -> navController.navigate(R.id.action_messageOverview_to_chat)
        }*/
    }



    override fun onCreate(savedInstanceState: Bundle?) {
        val intent = Intent(this.context, FragmentLatestMessages::class.java)
        //intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK.or(Intent.FLAG_ACTIVITY_NEW_TASK)
        startActivity(intent)


        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)

    }
/*

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.nav_top_menu, menu)

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        UtilView.menuOptionClick(item, requireActivity())
        return super.onOptionsItemSelected(item)
    }*/
}