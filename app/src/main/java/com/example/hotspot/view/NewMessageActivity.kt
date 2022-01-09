package com.example.hotspot.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.hotspot.R
import com.example.hotspot.databinding.ActivityNewMessageBinding
import com.example.hotspot.model.User
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObjects
import com.google.firebase.ktx.Firebase
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.kotlinandroidextensions.Item
import com.xwray.groupie.kotlinandroidextensions.GroupieViewHolder
import kotlinx.android.synthetic.main.user_row_new_message.view.*



class NewMessageActivity : AppCompatActivity() {

    //   private lateinit var recyclerView: RecyclerView
    private lateinit var binding: ActivityNewMessageBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNewMessageBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.title = "Select a Person"

        fetchUsers()
    }

    // we fetch the users from the database
    private fun fetchUsers(){

        val db = Firebase.firestore
        val userRef = db.collection("users")

        userRef.get()
            .addOnSuccessListener {
                val users = it.toObjects<User>()
                val adapter = GroupAdapter<com.xwray.groupie.GroupieViewHolder>()
                users.forEach { user ->
                    adapter.add(UserItem(user))
                }
                binding.recyclerviewNewMessage.adapter = adapter
            }
    }
}

    // this class is used to bind the fragment and user in order to change
    // the name element in the fragment to the real database name.

class UserItem(val user: User): Item() {
    override fun bind(viewHolder: GroupieViewHolder, position: Int) {

        viewHolder.itemView.row_message_name.text = user.name
    }

    override fun getLayout(): Int {
        return R.layout.user_row_new_message
    }
}
