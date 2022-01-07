package com.example.hotspot.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.hotspot.R
import com.example.hotspot.databinding.FragmentListeViewBinding

class ListeView : Fragment() {
private lateinit var binding: FragmentListeViewBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentListeViewBinding.inflate(layoutInflater)
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_liste_view, container, false)
    }

}