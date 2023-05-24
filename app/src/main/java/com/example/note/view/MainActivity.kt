package com.example.note.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.note.databinding.ActivityMainBinding
import com.example.note.model.db.Note
import com.example.note.util.coroutinesScope
import com.example.note.util.isBeforeTime
import com.example.note.util.noteDao
import com.example.note.util.nowTime
import com.example.note.util.timeToString
import com.example.note.viewmodel.NoteViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainActivity : AppCompatActivity() {
    private val notes: MutableList<Note> = mutableListOf()
    private lateinit var adapter: NoteAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val viewModel = ViewModelProvider(this)[NoteViewModel::class.java]
        adapter = NoteAdapter(notes)
        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        binding.recyclerView.adapter = adapter
        binding.add.setOnClickListener {
            add("note")
        }
        update()
        binding.add.setOnClickListener {
            update()
        }
    }

    private fun add(s: String) {
        Note().apply {
            content = s
            target = timeToString(nowTime())
        }.also {
            notes += it
            adapter.notifyItemInserted(notes.lastIndex)
            coroutinesScope.launch {
                noteDao.insert(it)
            }
        }
    }

    private fun update() {
        coroutinesScope.launch {
            noteDao.getAll().let {
                it.sortedWith { o1, o2 -> isBeforeTime(o1.target, o2.target) }
                notes.clear()
                notes.addAll(it)
                withContext(Dispatchers.Main) {
                    adapter.notifyDataSetChanged()
                }
            }
        }
    }
}