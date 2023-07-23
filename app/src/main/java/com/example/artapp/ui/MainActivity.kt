package com.example.artapp.ui

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.artapp.ArtApplication
import com.example.artapp.R
import com.example.artapp.model.Challenge
import com.example.artapp.ui.theme.ArtAppTheme
import com.example.artapp.viewmodel.ChallengeViewModel
import com.example.artapp.viewmodel.ChallengeViewModelFactory
import com.google.android.material.floatingactionbutton.FloatingActionButton

class MainActivity : ComponentActivity() {
    private val challengeViewModel: ChallengeViewModel by viewModels {
        ChallengeViewModelFactory((application as ArtApplication).repository)
    }
    private val newChallengeActivityRequestCode = 1

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
            startActivityForResult(intent, newChallengeActivityRequestCode)
        }
    }

    //TODO: deprecated
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == newChallengeActivityRequestCode && resultCode == Activity.RESULT_OK) {
            data?.getStringExtra(NewChallengeActivity.EXTRA_REPLY)?.let {
                val word = Challenge(it)
                challengeViewModel.insert(word)
            }
        } else {
            Toast.makeText(
                applicationContext,
                R.string.empty_not_saved,
                Toast.LENGTH_LONG).show()
        }

    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
            text = "Hello $name!",
            modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    ArtAppTheme {
        Greeting("Android")
    }
}