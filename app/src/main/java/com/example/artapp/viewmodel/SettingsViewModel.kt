package com.example.artapp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.artapp.database.ChallengeRepository
import com.example.artapp.database.PromptRepository
import com.example.artapp.entities.Challenge
import com.example.artapp.entities.Prompt
import com.example.artapp.models.ChallengeData
import kotlinx.coroutines.launch
import kotlinx.serialization.json.Json
import java.lang.IllegalArgumentException

class SettingsViewModel(private val challengeRepository: ChallengeRepository, private val promptRepository: PromptRepository) : ViewModel() {
    lateinit var importFinishedCommand: () -> Unit

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
        importFinishedCommand()
    }

    fun clearAndImport(list: String) = viewModelScope.launch {
        challengeRepository.deleteAll()
        promptRepository.deleteAll()
        import(list)
    }
}

class SettingsViewModelFactory(
    private val challengeRepository: ChallengeRepository,
    private val promptRepository: PromptRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(SettingsViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return SettingsViewModel(challengeRepository, promptRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }

}