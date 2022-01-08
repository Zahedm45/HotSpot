package com.example.hotspot.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.RecyclerView

import com.example.hotspot.R
import com.example.hotspot.databinding.ActivityNewMessageBinding
import com.example.hotspot.databinding.UserRowNewMessageBinding
import com.example.hotspot.model.User
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObjects
import com.google.firebase.ktx.Firebase
import com.squareup.picasso.Picasso
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.Item
import com.xwray.groupie.ViewHolder


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




    private fun fetchUsers(){

        val db = Firebase.firestore
        val userRef = db.collection("users")

        userRef.get()
            .addOnSuccessListener {
                val users = it.toObjects<User>()
                val adapter = GroupAdapter<ViewHolder>()
                users.forEach { user ->
                    adapter.add(UserItem(user))
                }
                binding.recyclerviewNewMessage.adapter = adapter
            }
    }
}

class UserItem(val user: User): Item<ViewHolder>() {

    override fun bind(viewHolder: ViewHolder, position: Int) {
        lateinit var bindingRow: UserRowNewMessageBinding
        viewHolder.itemView.apply {
            bindingRow.rowMessageName.text= user.name
        }
    }

    override fun getLayout(): Int {
        return R.layout.user_row_new_message
    }
}

/*
class CustomAdapter: RecyclerView.Adapter<ViewHolder> {
    override fun onBindViewHolder(p0:, p1: Int) {

    }
}*/