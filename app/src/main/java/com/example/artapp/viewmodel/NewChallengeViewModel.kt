package com.example.artapp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.artapp.database.ChallengeRepository
import com.example.artapp.database.PromptRepository
import com.example.artapp.entities.Challenge
import com.example.artapp.entities.Prompt
import kotlinx.coroutines.launch

class NewChallengeViewModel(private val challengeRepository: ChallengeRepository, private val promptRepository: PromptRepository): ViewModel() {
    fun insert(challenge: Challenge, prompts: MutableList<Prompt>) = viewModelScope.launch {
        val challengeId = challengeRepository.insert(challenge)
        for(prompt in prompts)
        {
            prompt.challengeId = challengeId
        }
        promptRepository.insertAll(prompts)
    }
}

class NewChallengeViewModelFactory(private val challengeRepository: ChallengeRepository, private val promptRepository: PromptRepository): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(NewChallengeViewModel::class.java)) {
            return NewChallengeViewModel(challengeRepository, promptRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}