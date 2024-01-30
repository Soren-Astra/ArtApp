package com.example.artapp.ui

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.artapp.ArtApplication
import com.example.artapp.R
import com.example.artapp.ui.adapters.ChallengeListAdapter
import com.example.artapp.viewmodel.ChallengeListViewModel
import com.example.artapp.viewmodel.ChallengeListViewModelFactory
import com.google.android.material.floatingactionbutton.FloatingActionButton

class MainActivity : ComponentActivity() {
    private val challengeViewModel: ChallengeListViewModel by viewModels {
        ChallengeListViewModelFactory((application as ArtApplication).challengeRepository)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val recyclerView = findViewById<RecyclerView>(R.id.recyclerview)
        val fab = findViewById<FloatingActionButton>(R.id.fab)
        val adapter = ChallengeListAdapter()

        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this)

        challengeViewModel.allChallenges.observe(this, Observer { challenges ->
            challenges?.let { adapter.submitList(it)}
        })
        fab.setOnClickListener {
            val intent = Intent(this@MainActivity, NewChallengeActivity::class.java)
            startActivity(intent)
        }
    }
}