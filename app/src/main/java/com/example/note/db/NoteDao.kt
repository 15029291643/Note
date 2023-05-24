package com.example.note.db

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query

@Dao
interface NoteDao {
    @Insert
    fun add(note: Note)

    @Delete
    fun remove(note: Note)

    @Query("select * from note")
    fun getAll(): List<Note>
}