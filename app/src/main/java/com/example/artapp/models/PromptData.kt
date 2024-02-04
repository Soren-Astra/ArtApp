package com.example.artapp.models

import kotlinx.serialization.Serializable

@Serializable
data class PromptData(
    val title: String = "",
    val description: String = "")
