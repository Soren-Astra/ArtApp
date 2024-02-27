package com.example.artapp.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.artapp.database.ChallengeRepository
import com.example.artapp.database.PromptRepository
import com.example.artapp.entities.Challenge
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import java.lang.IllegalArgumentException

class ChallengeListViewModel(
    private val challengeRepository: ChallengeRepository,
    private val promptRepository: PromptRepository) : ViewModel() {

    val allChallenges: MutableLiveData<MutableList<Challenge>> by lazy { MutableLiveData<MutableList<Challenge>>() }

    fun loadChallenges() = viewModelScope.launch {
        val challenges = challengeRepository.allChallenges.stateIn(viewModelScope).value
        for (challenge in challenges)
        {
            val prompts = promptRepository.getByChallenge(challenge.id)
            challenge.promptCount = prompts.size
            challenge.completedPromptCount = prompts.count { it.isDone }
        }
        allChallenges.value = challenges
    }
}

class ChallengeListViewModelFactory(
    private val challengeRepository: ChallengeRepository,
    private val promptRepository: PromptRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ChallengeListViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return ChallengeListViewModel(challengeRepository, promptRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}