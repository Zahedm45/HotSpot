package com.example.hotspot.view

import android.os.Bundle
import android.view.*
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.example.hotspot.R
import com.example.hotspot.databinding.FragmentFavoritesBinding
import com.example.hotspot.model.HotSpot
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObjects
import com.google.firebase.ktx.Firebase
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.GroupieViewHolder
import com.xwray.groupie.kotlinandroidextensions.Item
import kotlinx.android.synthetic.main.favorite_item.view.*



class Favorites : Fragment() {

    lateinit var navController: NavController
    private lateinit var binding: FragmentFavoritesBinding


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
        fetchFavoriteHotspots()

    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentFavoritesBinding.bind(view)
        navController = Navigation.findNavController(view)



    }


    private fun fetchFavoriteHotspots(){
        val db = Firebase.firestore
        val fbUser = Firebase.auth.uid.toString()
        val favoriteHotspotRef = db
            .collection("users").document(fbUser)
            .collection("favoriteHotspots")

        favoriteHotspotRef.get()
            .addOnSuccessListener {
                val hotspots = it.toObjects<HotSpot>()
                val adapter = GroupAdapter<GroupieViewHolder>()
                hotspots.forEach{hotSpot ->
                adapter.add(HotSpotItem(hotSpot))
                }
                binding.RVfavorites.adapter = adapter
            }
    }

    class HotSpotItem(val hotspot: HotSpot): Item() {
        override fun bind(
            viewHolder: com.xwray.groupie.kotlinandroidextensions.GroupieViewHolder,
            position: Int
        ) {


            viewHolder.itemView.deleteButton.setOnClickListener{
                // TODO: should delete item from list of favorite hotspots + update the database
            }
            viewHolder.itemView.hotspot_name.text = hotspot.hotSpotName
            viewHolder.itemView.hotspot_rating.text = hotspot.overallRating.toString()
            viewHolder.itemView.hotspot_location.text = hotspot.address?.town
        }

        override fun getLayout(): Int {
            return R.layout.favorite_item
        }
    }


}