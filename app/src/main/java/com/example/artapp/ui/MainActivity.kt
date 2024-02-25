package com.example.artapp.ui

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.artapp.ArtApplication
import com.example.artapp.R
import com.example.artapp.ui.adapters.ChallengeListAdapter
import com.example.artapp.ui.fragments.ImportDialogFragment
import com.example.artapp.viewmodel.ChallengeListViewModel
import com.example.artapp.viewmodel.ChallengeListViewModelFactory
import com.google.android.material.floatingactionbutton.FloatingActionButton

class MainActivity : AppCompatActivity() {
    private val challengeListViewModel: ChallengeListViewModel by viewModels {
        ChallengeListViewModelFactory((application as ArtApplication).challengeRepository, (application as ArtApplication).promptRepository)
    }
    private lateinit var _dialog: ImportDialogFragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val recyclerView = findViewById<RecyclerView>(R.id.recyclerview)
        val fab = findViewById<FloatingActionButton>(R.id.fab)
        val adapter = ChallengeListAdapter()
        _dialog = ImportDialogFragment(challengeListViewModel)
        challengeListViewModel.loadChallenges()

        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this)

        challengeListViewModel.allChallenges.observe(this, Observer { challenges ->
            challenges?.let { adapter.submitList(it)}
        })
        fab.setOnClickListener {
            val intent = Intent(this@MainActivity, NewChallengeActivity::class.java)
            startActivity(intent)
        }
    }

    override fun onResume() {
        super.onResume()
        challengeListViewModel.loadChallenges()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater: MenuInflater = menuInflater
        inflater.inflate(R.menu.menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.import_json -> {
                _dialog.show(supportFragmentManager, "IMPORT_DIALOG")
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

}