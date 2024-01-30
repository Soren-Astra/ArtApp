package com.example.artapp.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.asLiveData
import com.example.artapp.database.ChallengeRepository
import com.example.artapp.model.Challenge
import java.lang.IllegalArgumentException

class ChallengeListViewModel(private val repository: ChallengeRepository) : ViewModel() {

    val allChallenges: LiveData<List<Challenge>> = repository.allChallenges.asLiveData()

}

class ChallengeListViewModelFactory(private val repository: ChallengeRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ChallengeListViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return ChallengeListViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}