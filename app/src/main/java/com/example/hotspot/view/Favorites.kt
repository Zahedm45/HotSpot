package com.example.hotspot.view

import android.content.ContentValues.TAG
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.example.hotspot.R
import com.example.hotspot.databinding.FragmentFavoritesBinding
import com.example.hotspot.model.HotSpot
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObject
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
        val favoriteHotspotsRef = db
            .collection("users").document(fbUser)
            .collection("favoriteHotspots")

        favoriteHotspotsRef.get()
            .addOnSuccessListener { qr ->
                val hotspots = qr.documents
                val adapter = GroupAdapter<GroupieViewHolder>()
                hotspots.forEach{hotSpot ->
                    resolveHotspotRef((hotSpot.get("hotspot") as DocumentReference).id, adapter)
                }
                binding.RVfavorites.adapter = adapter
                adapter.setOnItemClickListener{ item, view ->
                   val
                   val intent = Intent(context,LoginActivity::class.java)
                   startActivity(intent)
                    Log.d(TAG,"123")
                }


            }
    }
    private fun resolveHotspotRef(ref : String, adapter: GroupAdapter<GroupieViewHolder>){
        val db = Firebase.firestore
        val favoriteHotspotsRef = db
            .collection("hotSpots").document(ref)
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

            viewHolder.itemView.deleteButton.setOnClickListener{
                // TODO: should delete item from list of favorite hotspots + update the database
            }
            viewHolder.itemView.hotspot_name.text = hotspot.name
            viewHolder.itemView.hotspot_rating.text = hotspot.rating.toString()
            viewHolder.itemView.hotspot_location.text = hotspot.address?.town
        }

        override fun getLayout(): Int {
            return R.layout.favorite_item
        }
    }


}