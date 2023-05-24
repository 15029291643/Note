package com.example.note

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.note.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private val notes: MutableList<Note> = mutableListOf()
    private lateinit var adapter: NoteAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
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
        notes += note
        adapter.notifyItemInserted(notes.lastIndex)
    }

    private fun update() {
        adapter.notifyDataSetChanged()
    }
}