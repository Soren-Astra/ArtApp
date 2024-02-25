package com.example.artapp.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.artapp.entities.Prompt
import kotlinx.coroutines.flow.Flow

@Dao
interface PromptDao {
    @Query("SELECT * FROM prompt ORDER BY title ASC")
    fun getAlphabetizedPrompts(): Flow<List<Prompt>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(prompt: Prompt)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertAll(prompts: List<Prompt>)

    @Query("SELECT * FROM prompt WHERE challenge_id = :challengeId")
    suspend fun getByChallenge(challengeId: Int): List<Prompt>

    @Query("DELETE FROM prompt")
    suspend fun deleteAll()

    @Query("DELETE FROM prompt WHERE challenge_id = :challengeId")
    suspend fun deleteFromChallenge(challengeId: Int)
}