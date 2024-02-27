package com.example.artapp.ui

import android.os.Bundle
import android.view.View
import androidx.activity.ComponentActivity
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.artapp.ArtApplication
import com.example.artapp.databinding.ActivityNewChallengeBinding
import com.example.artapp.entities.Challenge
import com.example.artapp.entities.Prompt
import com.example.artapp.ui.adapters.PromptAddListAdapter
import com.example.artapp.viewmodel.NewChallengeViewModel
import com.example.artapp.viewmodel.NewChallengeViewModelFactory

class NewChallengeActivity : ComponentActivity() {
    private lateinit var _binding: ActivityNewChallengeBinding
    private lateinit var addPromptAdapter: PromptAddListAdapter
    private var promptList: MutableList<Prompt> = mutableListOf()

    private val newChallengeViewModel: NewChallengeViewModel by viewModels {
        NewChallengeViewModelFactory(
            (application as ArtApplication).challengeRepository,
            (application as ArtApplication).promptRepository)
    }

    public override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityNewChallengeBinding.inflate(layoutInflater)
        setContentView(_binding.root)
        addPromptAdapter = PromptAddListAdapter()

        _binding.recyclerPromptAdd.adapter = addPromptAdapter
        _binding.recyclerPromptAdd.layoutManager = LinearLayoutManager(this)
        registerListeners()

    }

    private fun registerListeners() {
        _binding.buttonSave.setOnClickListener {
            _binding.progressBar.visibility = View.VISIBLE
            val challenge = Challenge(
                _binding.editName.text.toString(),
                _binding.editDescription.text.toString()
            )
            newChallengeViewModel.insert(challenge, addPromptAdapter.currentList.toMutableList())
            finish()
        }
        _binding.buttonCancel.setOnClickListener {
            finish()
        }
        _binding.buttonAddPrompt.setOnClickListener {
            promptList = addPromptAdapter.currentList.toMutableList()
            promptList.add(Prompt(_binding.editAddPrompt.text.toString(), "", 0))
            addPromptAdapter.submitList(promptList)
            addPromptAdapter.notifyItemInserted(promptList.size - 1)
            _binding.editAddPrompt.setText("")
        }
    }
}