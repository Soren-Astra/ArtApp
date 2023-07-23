package com.example.artapp.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.artapp.database.ChallengeRepository
import com.example.artapp.model.Challenge
import kotlinx.coroutines.launch
import java.lang.IllegalArgumentException

class ChallengeViewModel(private val repository: ChallengeRepository) : ViewModel() {

    val allChallenges: LiveData<List<Challenge>> = repository.allChallenges.asLiveData()

    fun insert(challenge: Challenge) = viewModelScope.launch {
        repository.insert(challenge)
    }
}

class ChallengeViewModelFactory(private val repository: ChallengeRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ChallengeViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return ChallengeViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}