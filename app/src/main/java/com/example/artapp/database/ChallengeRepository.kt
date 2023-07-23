package com.example.artapp.database

import androidx.annotation.WorkerThread
import com.example.artapp.model.Challenge
import kotlinx.coroutines.flow.Flow

class ChallengeRepository(private val challengeDao: ChallengeDao) {

    val allChallenges: Flow<List<Challenge>> = challengeDao.getAlphabetizedChallenges()

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun insert(challenge: Challenge) {
        challengeDao.insert(challenge)
    }
}