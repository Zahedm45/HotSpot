package com.example.hotspot.view.Chat

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.hotspot.R
import com.example.hotspot.databinding.FragmentNewMessageBinding
import com.example.hotspot.model.User
import com.example.hotspot.repository.Repository
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObjects
import com.google.firebase.ktx.Firebase
import com.squareup.picasso.Picasso
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.kotlinandroidextensions.Item
import com.xwray.groupie.kotlinandroidextensions.GroupieViewHolder
import kotlinx.android.synthetic.main.user_row_new_message.view.*



class NewMessage : Fragment() {

    //   private lateinit var recyclerView: RecyclerView
    private lateinit var binding: FragmentNewMessageBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?) : View? {
        val view = inflater.inflate(R.layout.fragment_new_message,container, false)
        binding = FragmentNewMessageBinding.bind(view)
        //supportActionBar?.hide()
        fetchUsers()

        return view
    }

    companion object {
        val USER_KEY = "USER_KEY"
    }

    // we fetch the users from the database
    private fun fetchUsers(){

        val db = Firebase.firestore
        val userRef = db.collection("users")
        val uid = FirebaseAuth.getInstance().uid
        val hotspotId = Repository.getUserProfile {  }

        userRef.get()
            .addOnSuccessListener {
                val users = it.toObjects<User>()
                val adapter = GroupAdapter<com.xwray.groupie.GroupieViewHolder>()
                users.forEach { user ->
                    if (user.uid != uid && user.uid != null  ) {

                        adapter.add(UserItem(user, user.uid!!))
                        adapter.setOnItemClickListener { item, view ->
                            val userItem = item as UserItem
                            val action = NewMessageDirections.actionNewmessageToChatlog(userItem.user.uid!!,userItem.user.name!!)
                            findNavController().navigate(action)
                        }
                    }
                }

                binding.recyclerviewNewMessage.adapter = adapter
            }
    }
}

    // this class is used to bind the fragment and user in order to change
    // the name element in the fragment to the real database name.

class UserItem(val user: User, val uid: String): Item() {
    override fun bind(viewHolder: GroupieViewHolder, position: Int) {

        viewHolder.itemView.row_message_name.text = user.name
        viewHolder.itemView.tv_age.text = user.age.toString()
        viewHolder.itemView.tv_gender.text = user.gender.toString()




        //TO-DO: create code for fetching user profile picture
        //val user = intent.getParcelableExtra<User>(USER_KEY)
        val imageId = uid

        val ref = "https://firebasestorage.googleapis.com/v0/b/hotspot-onmyown.appspot.com" +
                "/o/images%2F" + imageId + "?alt=media&token="

        val targetImage = viewHolder.itemView.profile_pic_chat
        Picasso.get()
            .load(ref)
            .into(targetImage)
    }

    override fun getLayout(): Int {
        return R.layout.user_row_new_message
    }
}


