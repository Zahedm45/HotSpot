package com.example.hotspot.view

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.appcompat.app.AppCompatActivity
import com.example.hotspot.R
import com.example.hotspot.other.UtilView

class Favorites : Fragment() {
    /* Dont know what these are for?
    private var param1: String? = null
    private var param2: String? = null */

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.fragment_favorites, container, false)
        (activity as AppCompatActivity?)!!.supportActionBar!!.title = "My favorites"
        return view
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