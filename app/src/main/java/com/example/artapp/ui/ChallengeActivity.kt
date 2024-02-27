package com.example.artapp.ui

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import com.example.artapp.ArtApplication
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
        promptListAdapter = PromptDisplayListAdapter(::onTogglePromptCompletion)
        _challengeId = intent.getIntExtra("challenge_id", 0)
        challengeViewModel.loadChallenge(_challengeId)

        _binding.challengePromptList.adapter = promptListAdapter
        _binding.challengePromptList.layoutManager = GridLayoutManager(this, 3)

        challengeViewModel.currentChallenge.observe(this) { challenge ->
            currentChallenge = challenge
            _binding.challengeName.text = currentChallenge.title
            _binding.challengeDescription.text = currentChallenge.description
        }
        challengeViewModel.challengePrompts.observe(this) { prompts ->
            promptListAdapter.submitList(prompts)
        }
    }

    private fun onTogglePromptCompletion(promptId: Int)
    {
        challengeViewModel.togglePromptCompletion(promptId)
    }
}