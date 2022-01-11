package com.example.hotspot.view

import android.os.Bundle
import android.view.*
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.hotspot.R
import com.example.hotspot.databinding.FragmentFavoritesBinding
import com.example.hotspot.model.HotSpot
import com.example.hotspot.model.User
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObjects
import com.google.firebase.ktx.Firebase
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.GroupieViewHolder
import com.xwray.groupie.kotlinandroidextensions.Item
import kotlinx.android.synthetic.main.favorite_item.view.*



class Favorites : Fragment() {

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
        fetchHotspots()

    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentFavoritesBinding.bind(view)



    }

    /* TODO: Currently testing with fetchUsers() --> should be changed so it fetches favorite hotspots */

    /*private fun fetchUsers(){

        val db = Firebase.firestore
        val userRef = db.collection("users")

        userRef.get()
            .addOnSuccessListener{
                val users = it.toObjects<User>()
                val adapter = GroupAdapter<GroupieViewHolder>()
                users.forEach{user->
                    adapter.add(UserItem(user))
                }
               binding.RVfavorites.adapter = adapter
            }
    }*/

    private fun fetchHotspots(){
        val db = Firebase.firestore
        val userRef = db.collection("hotSpots")

        userRef.get()
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
            viewHolder.itemView.hotspot_name.text = hotspot.hotSpotName
        }

        override fun getLayout(): Int {
            return R.layout.favorite_item
        }
    }

    /*class UserItem(val user: User): Item() {


        override fun bind(
            viewHolder: com.xwray.groupie.kotlinandroidextensions.GroupieViewHolder,
            position: Int
        ) {

            viewHolder.itemView.hotspot_name.text = user.name
        }

        override fun getLayout(): Int {
            return R.layout.favorite_item
        }
    } */


}