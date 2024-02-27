package com.example.artapp.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.artapp.entities.Challenge
import com.example.artapp.entities.Prompt
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Database(entities = [Challenge::class, Prompt::class], version = 2, exportSchema = false)
abstract class ChallengeDatabase : RoomDatabase() {

    abstract fun challengeDao() : ChallengeDao
    abstract fun promptDao() : PromptDao

    companion object {
        @Volatile
        private var INSTANCE: ChallengeDatabase? = null

        fun getDatabase(context: Context, scope: CoroutineScope): ChallengeDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    ChallengeDatabase::class.java,
                    "challenge"
                ).addCallback(ChallengeDatabaseCallback(scope)).build()
                INSTANCE = instance
                instance
            }
        }
    }

    private class ChallengeDatabaseCallback(
        private val scope: CoroutineScope
        ) : Callback() {
        override fun onCreate(db: SupportSQLiteDatabase) {
            super.onCreate(db)
            INSTANCE?.let { database ->
                scope.launch {
                    populateDatabase(database.challengeDao())
                }
            }
        }

        suspend fun populateDatabase(challengeDao: ChallengeDao) {
            // Delete all content here.
            challengeDao.deleteAll()
        }
    }
}