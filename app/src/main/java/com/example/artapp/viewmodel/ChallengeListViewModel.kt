package com.example.artapp.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.artapp.database.ChallengeRepository
import com.example.artapp.database.PromptRepository
import com.example.artapp.entities.Challenge
import com.example.artapp.entities.Prompt
import com.example.artapp.models.ChallengeData
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import kotlinx.serialization.json.Json
import java.lang.IllegalArgumentException

class ChallengeListViewModel(
    private val challengeRepository: ChallengeRepository,
    private val promptRepository: PromptRepository) : ViewModel() {

    val allChallenges: MutableLiveData<MutableList<Challenge>> by lazy { MutableLiveData<MutableList<Challenge>>() }

    fun loadChallenges() = viewModelScope.launch {
        val challenges = challengeRepository.allChallenges.stateIn(viewModelScope).value
        if (challenges != null) {
            for (challenge in challenges)
            {
                val prompts = promptRepository.getByChallenge(challenge.id)
                challenge.promptCount = prompts.size
                challenge.completedPromptCount = prompts.count { it.isDone }
            }
        }
        allChallenges.value = challenges
    }

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