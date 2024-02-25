package com.example.artapp.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey

@Entity(tableName = "challenge")
data class Challenge(
    @ColumnInfo(name = "title") var title: String = "",
    @ColumnInfo(name = "description") var description: String = ""
) {
    @PrimaryKey(autoGenerate = true) var id: Int = 0
    @Ignore var promptCount: Int = 0
    @Ignore var completedPromptCount: Int = 0
}