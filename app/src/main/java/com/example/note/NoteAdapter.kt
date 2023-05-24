package com.example.note

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.view.ViewTreeObserver.OnGlobalLayoutListener
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import androidx.recyclerview.widget.RecyclerView
import com.example.note.databinding.HolderNoteBinding
import com.example.note.db.Note
import com.example.note.util.setHeightByLines


class NoteAdapter(val notes: MutableList<Note>) : RecyclerView.Adapter<NoteAdapter.NodeHolder>() {
    private lateinit var context: Context

    class NodeHolder(binding: HolderNoteBinding) : RecyclerView.ViewHolder(binding.root) {
        val edit = binding.edit
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NodeHolder {
        context = parent.context
        val binding = HolderNoteBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return NodeHolder(binding)
    }

    override fun getItemCount(): Int {
        return notes.size
    }

    override fun onBindViewHolder(holder: NodeHolder, position: Int) {
        holder.edit.setText(notes[position].content)
        holder.edit.viewTreeObserver.addOnGlobalLayoutListener(object : OnGlobalLayoutListener {
            override fun onGlobalLayout() {
                holder.edit.viewTreeObserver.removeOnGlobalLayoutListener(this)
                holder.edit.setHeightByLines(10)
            }
        })
        holder.edit.addTextChangedListener {
            holder.edit.setHeightByLines(10)
        }
        holder.edit.setOnLongClickListener {
            Toast.makeText(
                holder.edit.context,
                "长按: ",
                Toast.LENGTH_SHORT
            ).show()
            true
        }
    }
}
