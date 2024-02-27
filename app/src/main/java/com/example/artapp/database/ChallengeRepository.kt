package com.example.artapp.database

import androidx.annotation.WorkerThread
import com.example.artapp.entities.Challenge
import kotlinx.coroutines.flow.Flow

class ChallengeRepository(private val challengeDao: ChallengeDao) {

    val allChallenges: Flow<MutableList<Challenge>> = challengeDao.getAlphabetizedChallenges()

    @WorkerThread
    suspend fun insert(challenge: Challenge): Int {
        return challengeDao.insert(challenge).toInt()
    }

    @WorkerThread
    suspend fun findById(id: Int): Challenge {
        return challengeDao.findById(id)
    }

    @WorkerThread
    suspend fun getIdForTitle(title: String): List<Long> {
        return challengeDao.getIdForTitle(title)
    }

    @WorkerThread
    suspend fun deleteAll() {
        challengeDao.deleteAll()
    }

    @WorkerThread
    suspend fun deleteFromTitle(title: String) {
        challengeDao.deleteFromTitle(title)
    }
}