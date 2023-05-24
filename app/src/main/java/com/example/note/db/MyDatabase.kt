package com.example.note.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import kotlin.reflect.KClass

@Database(entities = [Note::class], version = 1)
abstract class MyDatabase private constructor() : RoomDatabase() {
    abstract fun noteDao(): NoteDao
}