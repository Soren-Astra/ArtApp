package com.example.artapp.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "challenge")
data class Challenge(
    @ColumnInfo(name = "title") val title: String
) {
    @PrimaryKey(autoGenerate = true) var id: Int = 0
}