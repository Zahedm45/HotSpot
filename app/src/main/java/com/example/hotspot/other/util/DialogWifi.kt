package com.example.hotspot.other.util

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import androidx.fragment.app.DialogFragment
import com.example.hotspot.R

class DialogWifi : DialogFragment() {
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog =
        AlertDialog.Builder(requireContext())
            .setTitle("Internet disconnected.")
            .setMessage("Please connect to the Internet to continue using Hotspot.")
            .setIcon(R.drawable.vector_no_wifi_colored)
            .setNegativeButton("Ok"){ which, hello ->}
            .create()

    companion object {
        const val TAG = "PurchaseConfirmationDialog"
    }
}