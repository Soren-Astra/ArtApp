package com.example.artapp.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.artapp.model.Challenge
import kotlinx.coroutines.flow.Flow

@Dao
interface ChallengeDao {
    @Query("SELECT * FROM challenge ORDER BY title ASC")
    fun getAlphabetizedChallenges(): Flow<List<Challenge>>

    @Query("SELECT * FROM challenge WHERE id = :id")
    suspend fun findById(id: Int): Challenge

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(challenge: Challenge): Long

    @Query("DELETE FROM challenge")
    suspend fun deleteAll()
}