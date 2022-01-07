package com.example.hotspot.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.hotspot.R
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.Item
import com.xwray.groupie.ViewHolder

class NewMessageActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_message)

        supportActionBar?.title = "Select a Person"

        val adapter = GroupAdapter<ViewHolder>()
        adapter.add

        recyclerView = findViewById(R.id.recyclerview_newMessage)
        recyclerView.adapter
        //layoutmanager set in xml file -> recyclerView.layoutManager = LinearLayoutManager(this)


    }
}

class UserItem: Item<ViewHolder>() {
    override fun bind(viewHolder: ViewHolder, position: Int) {

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
