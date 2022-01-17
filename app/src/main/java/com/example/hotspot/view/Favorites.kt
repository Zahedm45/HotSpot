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
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import com.squareup.picasso.Picasso
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.GroupieViewHolder
import com.xwray.groupie.Section
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

            }


            }

    }
    private fun resolveHotspotRef(ref : String, adapter: GroupAdapter<GroupieViewHolder>){
        val db = Firebase.firestore
        val section = Section()
        val favoriteHotspotsRef = db
            .collection("hotSpots2").document(ref)
        favoriteHotspotsRef.get()
            .addOnSuccessListener { task ->
                if (task.exists()) {
                    val hotspot = task.toObject<HotSpot>()
                    if (hotspot is HotSpot) {
                        //adapter.add(HotSpotItem(hotspot))
                        section.add(HotSpotItem(hotspot))
                        adapter.add(section)
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


                        itemView.setOnClickListener {
                            val action = FavoritesDirections.actionFavoritesToBeforeCheckIn(hotspot)
                            Navigation.findNavController(viewHolder.itemView).navigate(action)

                        }

                    }


                }
            }

        }

            override fun getLayout(): Int {
                return R.layout.favorite_item
            }
        }








