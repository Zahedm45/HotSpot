package com.example.hotspot.view.infoWindow

import android.app.Activity
import android.content.Context
import android.view.View
import android.widget.TextView
import com.example.hotspot.R
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.Marker





class InfoWindow(val context: Context) : GoogleMap.InfoWindowAdapter {



    var mWindow = (context as Activity).layoutInflater.inflate(R.layout.info_window, null)

    private fun rendowWindowText(marker: Marker, view: View){

        val tvTitle = view.findViewById<TextView>(R.id.info_window_title)
        val tvSnippet = view.findViewById<TextView>(R.id.info_window_snippet)

        tvTitle.text = marker.title
        tvSnippet.text = marker.snippet

    }

    override fun getInfoContents(marker: Marker): View {
        rendowWindowText(marker, mWindow)
        return mWindow
    }

    override fun getInfoWindow(marker: Marker): View? {
        rendowWindowText(marker, mWindow)
        return mWindow
    }
}









/*
class InfoWindow(val context: Context) : GoogleMap.InfoWindowAdapter {




    val infoWindow = (context as Activity)
        .layoutInflater.inflate(R.layout.info_window, null)



    private fun setWindowText(marker: Marker, view: View) {

//        val title = view.findViewById<TextView>(R.id.info_window_title)
//        val desc = view.findViewById<TextView>(R.id.info_window_snippet)
//
//        title.text = marker.title
//        desc.text = marker.snippet


        val tvTitle = view.findViewById<TextView>(R.id.info_window_title)
        val tvSnippet = view.findViewById<TextView>(R.id.info_window_snippet)

        tvTitle.text = marker.title
        tvSnippet.text = marker.snippet


    }


    override fun getInfoContents(p0: Marker): View? {
        setWindowText(p0, infoWindow)
        return infoWindow
    }

    override fun getInfoWindow(p0: Marker): View? {
        setWindowText(p0, infoWindow)
        return infoWindow
    }
}*/
