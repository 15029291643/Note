package com.example.note

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.room.Room
import com.example.note.databinding.ActivityMainBinding
import com.example.note.db.MyDatabase
import com.example.note.db.Note

class MainActivity : AppCompatActivity() {
    private lateinit var db: MyDatabase
    private val notes: MutableList<Note> = mutableListOf()
    private lateinit var adapter: NoteAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        db = Room.databaseBuilder(this, MyDatabase::class.java, "1").build()
        adapter = NoteAdapter(notes)
        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        binding.recyclerView.adapter = adapter
        binding.add.setOnClickListener {
            Note().apply {
                height = 100
                content = "2023-5-23 11:15"
            }.also {
                add(it)
            }
        }
        update()
    }

    private fun add(note: Note) {
        Thread {
            notes += note
            adapter.notifyItemInserted(notes.lastIndex)
            db.noteDao().add(note)
        }.start()
    }

    private fun update() {
        Thread {
            db.noteDao().getAll().let {
                notes.clear()
                notes.addAll(it)
            }
            adapter.notifyDataSetChanged()
        }.start()
    }
}