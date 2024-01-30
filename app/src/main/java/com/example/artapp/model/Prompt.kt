package com.example.artapp.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "prompt")
data class Prompt(
    @ColumnInfo(name = "title") val title: String,
    @ColumnInfo(name = "description") val description: String,
    @ColumnInfo(name = "challenge_id") var challengeId: Int,
    @ColumnInfo(name = "is_done") val isDone: Boolean = false
) {
    @PrimaryKey(autoGenerate = true) var id: Int = 0
}