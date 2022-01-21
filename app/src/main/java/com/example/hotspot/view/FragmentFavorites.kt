package com.example.hotspot.view


import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.compose.ui.node.getOrAddAdapter
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment.findNavController
import com.example.hotspot.R
import com.example.hotspot.databinding.FragmentFavoritesBinding
import com.example.hotspot.model.HotSpot
import com.example.hotspot.other.network.TAG
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.ktx.storage
import com.squareup.picasso.Picasso
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.GroupieViewHolder
import com.xwray.groupie.kotlinandroidextensions.Item
import kotlinx.android.synthetic.main.favorite_item.view.*
import kotlinx.android.synthetic.main.fragment_favorites.*


class Favorites : Fragment() {


    private lateinit var binding: FragmentFavoritesBinding


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_favorites, container, false)
        binding = FragmentFavoritesBinding.bind(view)
        return view


    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        fetchFavoriteHotspots()
        Log.i(TAG, "oncreate")


    }

    override fun onResume() {
        super.onResume()
        fetchFavoriteHotspots()


    }


    private fun fetchFavoriteHotspots() {
        val db = Firebase.firestore
        val fbUser = Firebase.auth.uid.toString()
        val favoriteHotspotsRef = db
            .collection("users").document(fbUser)
            .collection("favoriteHotspots")

        favoriteHotspotsRef.get()
            .addOnSuccessListener { qr ->
                val hotspots = qr.documents
                val adapter = GroupAdapter<GroupieViewHolder>()

                binding.RVfavorites.adapter = adapter
                hotspots.forEach { hotSpot ->
                    resolveHotspotRef(hotSpot.get("hotspotId") as String, adapter )
                }
                if (hotspots.isEmpty()){
                      binding.RVfavorites.visibility = View.GONE
                      binding.emptyHeartView.visibility = View.VISIBLE
                      binding.emptyListView.visibility = View.VISIBLE
                 } else {
                     binding.RVfavorites.visibility = View.VISIBLE
                    binding.emptyHeartView.visibility = View.GONE
                    binding.emptyListView.visibility = View.GONE
                 }
            }
    }
}




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
    }



    class HotSpotItem(val hotspot: HotSpot): Item() {
        override fun bind(
            viewHolder: com.xwray.groupie.kotlinandroidextensions.GroupieViewHolder,
            position: Int
        ) {
            viewHolder.apply {
                with(viewHolder.itemView) {
                    hotspot_name.text = hotspot.name
                    hotspot_rating.text = hotspot.rating.toString()
                    hotspot_location.text = hotspot.address?.town
                    val imageRef = Firebase.storage.reference.child("HotSpots/${hotspot.id}.png")
                    imageRef.downloadUrl.addOnSuccessListener { Uri ->
                        val imageUrl = Uri.toString()
                        val imageView = viewHolder.itemView.hotspot_picture
                        Picasso.get().load(imageUrl).into(imageView)

                    }

                }

                viewHolder.itemView.setOnClickListener {
                    val action = FavoritesDirections.actionFavoritesToBeforeCheckIn(hotspot)
                    it.findNavController().navigate(action)
                }

            }

        }


        override fun getLayout(): Int {
            return R.layout.favorite_item
        }


    }








