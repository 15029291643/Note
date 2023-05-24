package com.example.note

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.room.Room
import com.example.note.databinding.ActivityMainBinding
import com.example.note.db.Note
import com.example.note.db.NoteDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainActivity : AppCompatActivity() {
    private val notes: MutableList<Note> = mutableListOf()
    private lateinit var adapter: NoteAdapter
    private lateinit var db: NoteDatabase
    private val job = Job()
    private val coroutinesScope = CoroutineScope(Dispatchers.IO + job)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        db = Room.databaseBuilder(
            applicationContext,
            NoteDatabase::class.java, "database-name"
        ).build()
        adapter = NoteAdapter(notes)
        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        binding.recyclerView.adapter = adapter
        binding.add.setOnClickListener {
            add("note")
        }
        update()
    }

    private fun add(s: String) {
        Note().apply {
            height = 200
            content = s
        }.also {
            notes += it
            adapter.notifyItemInserted(notes.lastIndex)
            coroutinesScope.launch {
                db.noteDao().add(it)
            }
        }
    }

    private fun update() {
        coroutinesScope.launch {
            db.noteDao().getAll().also {
                notes.clear()
                notes.addAll(it)
                withContext(Dispatchers.Main) {
                    adapter.notifyDataSetChanged()
                }
            }
        }
    }
}