package com.example.artapp.models

import kotlinx.serialization.Serializable

@Serializable
data class ChallengeData(
    val title: String = "",
    val description: String = "",
    val prompts: List<PromptData>)
