package com.example.note.util

import com.example.note.model.db.NoteDatabase
import com.example.note.view.MyApp

val noteDao = NoteDatabase.getInstance(MyApp.context).noteDao()