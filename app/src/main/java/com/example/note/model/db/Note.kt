package com.example.note.model.db

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.note.util.timeToString
import java.time.LocalDateTime

@Entity
data class Note(
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0,
    var target: String = timeToString(LocalDateTime.now()),
    var content: String = ""
)
