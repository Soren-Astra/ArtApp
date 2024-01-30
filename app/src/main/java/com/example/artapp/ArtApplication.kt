package com.example.artapp

import android.app.Application
import com.example.artapp.database.ChallengeDatabase
import com.example.artapp.database.ChallengeRepository
import com.example.artapp.database.PromptRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob

class ArtApplication : Application() {
    val applicationScope = CoroutineScope(SupervisorJob())
    val database by lazy {ChallengeDatabase.getDatabase(this, applicationScope)}
    val challengeRepository by lazy {ChallengeRepository(database.challengeDao())}
    val promptRepository by lazy {PromptRepository(database.promptDao())}
}