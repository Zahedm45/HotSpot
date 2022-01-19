package com.example.hotspot.view

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.hotspot.R
import com.example.hotspot.databinding.ActivityNewMessageBinding
import com.example.hotspot.model.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObjects
import com.google.firebase.ktx.Firebase
import com.squareup.picasso.Picasso
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

        supportActionBar?.hide()

        fetchUsers()
    }

    companion object {
        val USER_KEY = "USER_KEY"
    }

    // we fetch the users from the database
    private fun fetchUsers(){

        val db = Firebase.firestore
        val userRef = db.collection("users")
        val uid = FirebaseAuth.getInstance().uid

        userRef.get()
            .addOnSuccessListener {
                val users = it.toObjects<User>()
                val adapter = GroupAdapter<com.xwray.groupie.GroupieViewHolder>()
                users.forEach { user ->
                    if (user.uid != uid) {
                        adapter.add(UserItem(user, user.uid!!))
                    }
                }
                adapter.setOnItemClickListener { item, view ->
                    val userItem = item as UserItem
                    val intent = Intent(view.context, ChatLogActivity::class.java)
                    intent.putExtra(USER_KEY, userItem.user)
                    startActivity(intent)
                    finish()
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


