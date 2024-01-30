package com.example.artapp.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.artapp.database.ChallengeRepository
import com.example.artapp.database.PromptRepository
import com.example.artapp.model.Challenge
import com.example.artapp.model.Prompt
import kotlinx.coroutines.launch
import java.lang.IllegalArgumentException


class ChallengeViewModel(private val challengeRepository: ChallengeRepository, private val promptRepository: PromptRepository) : ViewModel() {

    val currentChallenge: MutableLiveData<Challenge> by lazy { MutableLiveData<Challenge>() }
    val challengePrompts: MutableLiveData<List<Prompt>> by lazy { MutableLiveData<List<Prompt>>() }

    fun loadChallenge(challengeId: Int) = viewModelScope.launch {
        currentChallenge.value = challengeRepository.findById(challengeId)
        challengePrompts.value = promptRepository.getByChallenge(challengeId)
    }
}

class ChallengeViewModelFactory(private val challengeRepository: ChallengeRepository, private val promptRepository: PromptRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ChallengeViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return ChallengeViewModel(challengeRepository, promptRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}