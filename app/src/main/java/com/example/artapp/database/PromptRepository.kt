package com.example.artapp.database

import androidx.annotation.WorkerThread
import com.example.artapp.entities.Prompt

class PromptRepository(private val promptDao: PromptDao) {
    @WorkerThread
    suspend fun insertAll(prompts: List<Prompt>) {
        promptDao.insertAll(prompts)
    }

    @WorkerThread
    suspend fun getByChallenge(challengeId: Int): List<Prompt>
    {
        return promptDao.getByChallenge(challengeId)
    }

    @WorkerThread
    suspend fun deleteAll() {
        promptDao.deleteAll()
    }

    @WorkerThread
    suspend fun deleteFromChallenge(challengeId: Int)
    {
        promptDao.deleteFromChallenge(challengeId)
    }
}