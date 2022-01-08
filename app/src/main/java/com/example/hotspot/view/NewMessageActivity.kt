package com.example.hotspot.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.renderscript.ScriptGroup
import android.view.View
import androidx.fragment.app.findFragment
import androidx.lifecycle.ViewModel
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.hotspot.R
import com.example.hotspot.databinding.ActivityLatestMessagesBinding
import com.example.hotspot.databinding.ActivityNewMessageBinding
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.firestore.auth.User
import com.squareup.picasso.Picasso
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.Item
import com.xwray.groupie.ViewHolder

class NewMessageActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var binding: ActivityNewMessageBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNewMessageBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.title = "Select a Person"

        fetchUsers()
    }

    private fun fetchUsers(){
        val userRef = FirebaseDatabase.getInstance().getReference("/users")
        userRef.addListenerForSingleValueEvent(object: ValueEventListener{
            override fun onDataChange(p0: DataSnapshot){
                val adapter = GroupAdapter<ViewHolder>()
                p0.children.forEach {
                    val user = it.getValue(User::class.java)
                    if (user!=null) {
                        adapter.add(UserItem(user))
                    }
                }
            }
            override fun onCancelled(p0: DatabaseError){

            }
        })
    }
}

class UserItem(val user: User): Item<ViewHolder>() {
    override fun bind(viewHolder: ViewHolder, position: Int) {
        viewHolder.itemView.row_message_name
        Picasso.get().load(user.bitmapImg)
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
