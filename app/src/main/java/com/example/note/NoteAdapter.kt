package com.example.note

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.note.databinding.HolderNoteBinding
import com.example.note.db.Note

class NoteAdapter(val notes: MutableList<Note>) : RecyclerView.Adapter<NoteAdapter.NodeHolder>() {
    class NodeHolder(binding: HolderNoteBinding) :  RecyclerView.ViewHolder(binding.root){
        val edit = binding.edit
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NodeHolder {
        val binding = HolderNoteBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return NodeHolder(binding)
    }

    override fun getItemCount(): Int {
        return notes.size
    }

    override fun onBindViewHolder(holder: NodeHolder, position: Int) {
        holder.edit.setText(notes[position].content)
        holder.edit.height = notes[position].height
        holder.edit.setOnLongClickListener {
            Toast.makeText(holder.edit.context, "长按", Toast.LENGTH_SHORT).show()
            true
        }
    }
}