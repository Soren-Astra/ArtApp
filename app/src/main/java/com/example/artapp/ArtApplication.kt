package com.example.artapp

import android.app.Application
import com.example.artapp.database.ChallengeDatabase
import com.example.artapp.database.ChallengeRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob

class ArtApplication : Application() {
    val applicationScope = CoroutineScope(SupervisorJob())
    val database by lazy {ChallengeDatabase.getDatabase(this, applicationScope)}
    val repository by lazy {ChallengeRepository(database.challengeDao())}
}