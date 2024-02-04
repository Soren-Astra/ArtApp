package com.example.artapp.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.artapp.database.ChallengeRepository
import com.example.artapp.database.PromptRepository
import com.example.artapp.model.Challenge
import com.example.artapp.model.Prompt
import com.example.artapp.models.ChallengeData
import kotlinx.coroutines.launch
import kotlinx.serialization.json.Json
import java.lang.IllegalArgumentException

class ChallengeListViewModel(
    private val challengeRepository: ChallengeRepository,
    private val promptRepository: PromptRepository) : ViewModel() {

    val allChallenges: LiveData<List<Challenge>> = challengeRepository.allChallenges.asLiveData()

    fun import(list: String, replaceExisting: Boolean = false) = viewModelScope.launch {
        val objList = Json.decodeFromString<List<ChallengeData>>(list)
        //TODO: Can probably be optimized
        for (challenge in objList) {
            if (replaceExisting)
            {
                val oldIds = challengeRepository.getIdForTitle(challenge.title)
                for (oldId in oldIds)
                {
                    promptRepository.deleteFromChallenge(oldId.toInt())
                }
                challengeRepository.deleteFromTitle(challenge.title)
            }
            val challengeId = challengeRepository.insert(Challenge(challenge.title, challenge.description))
            val promptEntityList: MutableList<Prompt> = mutableListOf()
            for (prompt in challenge.prompts)
            {
                promptEntityList.add(Prompt(prompt.title, prompt.description, challengeId, false))
            }
            promptRepository.insertAll(promptEntityList)
        }
    }

    fun clearAndImport(list: String) = viewModelScope.launch {
        challengeRepository.deleteAll()
        promptRepository.deleteAll()
        import(list)
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