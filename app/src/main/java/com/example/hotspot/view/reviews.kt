package com.example.hotspot

import android.media.MediaDrm
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.hotspot.databinding.FragmentReviewsBinding
import com.example.hotspot.model.reviewItem
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.GroupieAdapter
import com.xwray.groupie.GroupieViewHolder
import kotlinx.android.synthetic.main.fragment_reviews.*
import kotlinx.android.synthetic.main.review_item.view.*

class reviews : Fragment() {

    private lateinit var binding: FragmentReviewsBinding
    var adapter = GroupAdapter<GroupieViewHolder>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
        //binding = FragmentReviewsBinding.inflate(layoutInflater)
        //recycler_view_reviews.adapter = adapter
        //setContentView(binding.root)

        fetchReviews()
        Log.i("debug", "oncreate")
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        Log.i("debug", "oncreateview")
        val view = inflater.inflate(R.layout.fragment_reviews,container,false)
        binding = FragmentReviewsBinding.bind(view)
        binding.recyclerViewReviews.adapter = adapter
        return view

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Log.i("debug", "onviewcreated")

    }

    override fun onResume() {
        super.onResume()

        Log.i("debug", "onResume")
    }

    private fun fetchReviews(){
        val db = Firebase.firestore
        val fbUser = Firebase.auth.uid.toString()
        val Reviews = db
            .collection("users").document(fbUser)
            .collection("Reviews")


        //val adapter = GroupAdapter<GroupieViewHolder>()

        //dummies made manuelly with adapter
        adapter.add(ReviewItem("Ben","Best place ever, totally coming back again"))
        adapter.add(ReviewItem("Jeniffer","I had a great night here, got to meet a lot of new people at this place"))
        adapter.add(ReviewItem("Iben","without doubt I will come by here again"))
        adapter.add(ReviewItem("Karen","Totally cool place, the people here are nice and the staff are also helpful for sure"))
        adapter.add(ReviewItem("Benny","I normally didnt go out that much, but this place have become my new home where I hang out with my friends and meet a lot of new people"))
        adapter.add(ReviewItem("Thomas","Super nice staff here and the people I got to meet here have become my good friends now"))
        adapter.add(ReviewItem("Jonas","Lovely place, the design of the interior is so well done"))
        adapter.add(ReviewItem("Micheal","I would come here again for sure"))
        adapter.add(ReviewItem("Albert","Im rating this 5/5 cause I like the place and the vibe in it is great, im coming again here for sure"))
        adapter.add(ReviewItem("Chris","Best bar ever, I just got to know this place from the app and im loving it. I got to know a lot of new people that lived so close to me"))
        adapter.add(ReviewItem("Sofie","Would recommend this place to everyone who wants to get out in the middle of the day without having to worry of drinking, they got cocktails with and without alcohol in them."))

        //binding.recyclerViewReviews.adapter = adapter

        /*HotspotReviews.get()
            .addOnSuccessListener { qr ->
                val reviews = qr.documents
                val adapter = GroupAdapter<com.xwray.groupie.GroupieViewHolder>()
                reviews.forEach { review ->
                    //resolveHotspotreview(hotSpot.get("hotspotId") as String, adapter)
                    adapter.add(ReviewItem("ben","Monke","test tekst"))
                    Log.d("test","test msg")
                }
                binding.recyclerViewReviews.adapter = adapter
            }
*/
    }
}
class ReviewItem(val reviewers_name: String, val review_content: String): com.xwray.groupie.kotlinandroidextensions.Item() {
    override fun bind(
        viewHolder: com.xwray.groupie.kotlinandroidextensions.GroupieViewHolder,
        position: Int
    ) {
        viewHolder.itemView.reviewers_name.text = reviewers_name
        viewHolder.itemView.review_content.text = review_content

        /*val imageId = user.uid
        val ref = "https://firebasestorage.googleapis.com/v0/b/hotspot-onmyown.appspot.com" +
                "/o/images%2F" + imageId + "?alt=media&token="
        val targetImage = viewHolder.itemView.latest_message_pic
        Picasso.get()
            .load(ref)
            .into(targetImage)
    */}

    override fun getLayout(): Int {
        return R.layout.review_item
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

         class ReviewItem(val hotspot: HotSpot): Item() {
        override fun bind(
            viewHolder: com.xwray.groupie.kotlinandroidextensions.GroupieViewHolder,
            position: Int
        ) {
            viewHolder.apply {
                with(viewHolder.itemView){
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
    */

