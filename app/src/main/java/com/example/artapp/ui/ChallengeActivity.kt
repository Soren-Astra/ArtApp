package com.example.artapp.ui

import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.artapp.ArtApplication
import com.example.artapp.R
import com.example.artapp.databinding.ActivityChallengeBinding
import com.example.artapp.entities.Challenge
import com.example.artapp.ui.adapters.PromptDisplayListAdapter
import com.example.artapp.viewmodel.ChallengeViewModel
import com.example.artapp.viewmodel.ChallengeViewModelFactory

class ChallengeActivity : AppCompatActivity() {
    private var _challengeId: Int = 0
    private lateinit var _binding: ActivityChallengeBinding
    private lateinit var currentChallenge: Challenge
    private lateinit var promptListAdapter: PromptDisplayListAdapter

    private val challengeViewModel: ChallengeViewModel by viewModels {
        ChallengeViewModelFactory(
            (application as ArtApplication).challengeRepository,
            (application as ArtApplication).promptRepository)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityChallengeBinding.inflate(layoutInflater)

        setContentView(_binding.root)
        promptListAdapter = PromptDisplayListAdapter()
        _challengeId = intent.getIntExtra("challenge_id", 0)
        challengeViewModel.loadChallenge(_challengeId)

        _binding.challengePromptlist.adapter = promptListAdapter
        _binding.challengePromptlist.layoutManager = LinearLayoutManager(this)

        challengeViewModel.currentChallenge.observe(this, Observer { challenge ->
            currentChallenge = challenge
            _binding.challengeName.text = currentChallenge.title
            _binding.challengeDescription.text = currentChallenge.description
        })
        challengeViewModel.challengePrompts.observe(this, Observer { prompts ->
            promptListAdapter.submitList(prompts)
        })
    }
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater: MenuInflater = menuInflater
        inflater.inflate(R.menu.menu, menu)
        title = currentChallenge.title
        return true
    }
}