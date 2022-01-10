package com.example.hotspot.view

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.appcompat.app.AppCompatActivity
import com.example.hotspot.R
import com.example.hotspot.model.User
import com.example.hotspot.other.UtilView
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObjects
import com.google.firebase.ktx.Firebase
import com.xwray.groupie.GroupAdapter
import android.view.View
import com.xwray.groupie.GroupieViewHolder
import com.xwray.groupie.Item
import kotlinx.android.synthetic.main.favorite_item.view.*
import androidx.recyclerview.widget.RecyclerView
import com.example.hotspot.databinding.FragmentFavoritesBinding


class Favorites : Fragment() {

    private lateinit var binding: FragmentFavoritesBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_favorites, container, false)
        binding = FragmentFavoritesBinding.inflate(inflater,container, false)
        (activity as AppCompatActivity?)!!.supportActionBar!!.title = "My favorites"
        return view
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
        fetchUsers()
    }

    /* TODO: Currently testing with fetchUsers() --> should be changed so it fetches favorite hotspots */

    private fun fetchUsers(){

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
    }


    class UserItem(val user: User): Item<GroupieViewHolder>() {
        override fun bind(viewHolder: GroupieViewHolder, position: Int) {

            viewHolder.itemView.hotspot_name.text = user.name
        }

        override fun getLayout(): Int {
            return R.layout.fragment_favorites
        }
    }


    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.nav_top_menu, menu)

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        UtilView.menuOptionClick(item, requireActivity())
        return super.onOptionsItemSelected(item)
    }
}