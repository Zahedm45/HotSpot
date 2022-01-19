package com.example.hotspot

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.hotspot.databinding.FragmentReviewsBinding
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.GroupieViewHolder

class reviews : Fragment() {

    private lateinit var binding: FragmentReviewsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
        //fetchReviews()
        //Log.i(TAG, "oncreate")
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_reviews, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentReviewsBinding.inflate(layoutInflater)

    }

    override fun onResume() {
        super.onResume()
        //fetchReviews()
    }

    private fun fetchReviews(){
        val db = Firebase.firestore
        val fbUser = Firebase.auth.uid.toString()
        val HotspotReviews = db
            .collection("users").document(fbUser)
            .collection("Reviews")


        HotspotReviews.get()
            .addOnSuccessListener { qr ->
                val reviews = qr.documents
                val adapter = GroupAdapter<GroupieViewHolder>()
                reviews.forEach { review ->
                    resolveHotspotreview(hotSpot.get("hotspotId") as String, adapter)
                }
                binding.recyclerViewReviews.adapter = adapter
            }
    }
}

    /*


    private fun resolveHotspotRef(ref : String, adapter: GroupAdapter<GroupieViewHolder>){
        val db = Firebase.firestore
        val favoriteHotspotsRef = db
            .collection("hotSpots3").document(ref)
        favoriteHotspotsRef.get()
            .addOnSuccessListener { task ->
                if (task.exists()) {
                    val hotspot = task.toObject<HotSpot>()
                    if (hotspot is HotSpot) {

                        adapter.add(HotSpotItem(hotspot))
                    }
                }
            }
    */

}