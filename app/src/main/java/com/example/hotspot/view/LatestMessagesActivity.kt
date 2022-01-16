package com.example.hotspot.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import com.example.hotspot.R
import com.example.hotspot.databinding.ActivityLatestMessagesBinding
import com.example.hotspot.model.User
import com.google.firebase.auth.FirebaseAuth
import com.xwray.groupie.GroupAdapter
import kotlinx.android.synthetic.main.activity_latest_messages.*

import com.google.firebase.firestore.*
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObjects
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.chat_to_row.view.*
import kotlinx.android.synthetic.main.latest_message_row.*
import kotlinx.android.synthetic.main.latest_message_row.view.*
import kotlinx.android.synthetic.main.user_row_new_message.view.*

private lateinit var binding: ActivityLatestMessagesBinding

class LatestMessagesActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLatestMessagesBinding.inflate(layoutInflater)
        setContentView(binding.root)
        fetchUsers()

//        verifyUserIsLoggedIn()

    }


    companion object {
        val USER_KEY = "USER_KEY"
    }

    private fun fetchUsers(){

        val db = Firebase.firestore
        val userRef = db.collection("users")

        val user = intent.getParcelableExtra<User>(NewMessageActivity.USER_KEY)
        val toid = user?.uid

        userRef.get()
            .addOnSuccessListener {
                val users = it.toObjects<User>()
                val adapter = GroupAdapter<com.xwray.groupie.GroupieViewHolder>()
                users.forEach { user ->
                    adapter.add(LatestMessageRow(user))
                }
                if ()
                adapter.setOnItemClickListener { item, view ->
                    val latestMessageRow = item as LatestMessageRow
                    val intent = Intent(view.context, ChatLogActivity::class.java)
                    intent.putExtra(NewMessageActivity.USER_KEY, latestMessageRow.user)
                    startActivity(intent)
                }
                binding.recyclerviewLatestMessages.adapter = adapter
            }
    }

    class LatestMessageRow(val user: User): com.xwray.groupie.kotlinandroidextensions.Item() {
        override fun bind(
            viewHolder: com.xwray.groupie.kotlinandroidextensions.GroupieViewHolder,
            position: Int
        ) {
            viewHolder.itemView.latest_message_name.text = user.name
        }

        override fun getLayout(): Int {
            return R.layout.latest_message_row
        }
    }



    private fun verifyUserIsLoggedIn() {
        //Perform check to see if user is logged in (if necessary)
        val uid = FirebaseAuth.getInstance().uid
        if (uid == null) {
            val intent = Intent(this, CreateProfileActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK.or(Intent.FLAG_ACTIVITY_NEW_TASK)
            startActivity(intent)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.nav_top_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        when (item?.itemId) {
            R.id.menu_new_message -> {
                val intent = Intent(this, NewMessageActivity::class.java)
                //intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK.or(Intent.FLAG_ACTIVITY_NEW_TASK)
                startActivity(intent)
            }
            R.id.menu_sign_out -> {
                FirebaseAuth.getInstance().signOut()
                //Run registration activity
                val intent = Intent(this, CreateProfileActivity::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK.or(Intent.FLAG_ACTIVITY_NEW_TASK)
                startActivity(intent)
            }
        }

        return super.onOptionsItemSelected(item)
    }
}
