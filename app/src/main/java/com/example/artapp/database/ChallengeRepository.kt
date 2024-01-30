package com.example.artapp.database

import androidx.annotation.WorkerThread
import com.example.artapp.model.Challenge
import kotlinx.coroutines.flow.Flow

class ChallengeRepository(private val challengeDao: ChallengeDao) {

    val allChallenges: Flow<List<Challenge>> = challengeDao.getAlphabetizedChallenges()

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun insert(challenge: Challenge): Int {
        return challengeDao.insert(challenge).toInt()
    }

    @WorkerThread
    suspend fun findById(id: Int): Challenge {
        return challengeDao.findById(id)
    }
}