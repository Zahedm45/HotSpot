package com.example.hotspot.view

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.appcompat.app.AppCompatActivity
import com.example.hotspot.R
import com.example.hotspot.databinding.ActivityAfterLoginBinding
import com.example.hotspot.databinding.FragmentFavoritesBinding
import com.example.hotspot.databinding.FragmentListeViewBinding
import com.example.hotspot.model.User
import com.example.hotspot.other.UtilView
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObjects
import com.google.firebase.ktx.Firebase
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.kotlinandroidextensions.GroupieViewHolder
import com.xwray.groupie.kotlinandroidextensions.Item
import kotlinx.android.synthetic.main.favorite_item.view.*



class Favorites : Fragment() {
    /* Dont know what these are for?
    private var param1: String? = null
    private var param2: String? = null */

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
        fetchUsers()


    }

    private fun fetchUsers(){

        val db = Firebase.firestore
        val userRef = db.collection("users")

        userRef.get()
            .addOnSuccessListener{
                val users = it.toObjects<User>()
                val adapter = GroupAdapter<com.xwray.groupie.GroupieViewHolder>()
                users.forEach{user->
                    adapter.add(UserItem(user))
                }
                binding.RVfavorites.adapter = adapter
            }
    }


    class UserItem(val user: User): Item() {
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