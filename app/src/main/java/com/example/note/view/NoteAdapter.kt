package com.example.note.view

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.view.ViewTreeObserver
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import androidx.recyclerview.widget.RecyclerView
import com.example.note.databinding.HolderNoteBinding
import com.example.note.model.db.Note
import com.example.note.util.coroutinesScope
import com.example.note.util.durationString
import com.example.note.util.noteDao
import com.example.note.util.setHeightByLines
import com.example.note.util.showToast
import kotlinx.coroutines.launch


class NoteAdapter(val notes: MutableList<Note>) : RecyclerView.Adapter<NoteAdapter.NodeHolder>() {
    private val TAG = "haojinhui"

    private lateinit var context: Context

    class NodeHolder(binding: HolderNoteBinding) : RecyclerView.ViewHolder(binding.root) {
        val content = binding.content
        val target = binding.target
        val time = binding.time
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
        holder.content.run {
            setText(notes[position].content)
            viewTreeObserver.addOnGlobalLayoutListener(object :
                ViewTreeObserver.OnGlobalLayoutListener {
                override fun onGlobalLayout() {
                    viewTreeObserver.removeOnGlobalLayoutListener(this)
                    setHeightByLines(10)
                }
            })
            addTextChangedListener {
                setHeightByLines()
                // 如果连续出现两个"\n"，创建一个新的edit
                if (text.toString().contains("\n\n")) {
                    showToast("出现了两个回车")
                    add("随便写点啥")
                }
                if (text.toString().isEmpty()) {
                    showToast("啥都没有")
                    remove(position)
                }
            }
            setOnLongClickListener {
                Toast.makeText(
                    context,
                    "长按: ",
                    Toast.LENGTH_SHORT
                ).show()
                true
            }
        }
        // 预定时间
        holder.target.run {
            setText(notes[position].target)
            addTextChangedListener{
                notes[position].target = it.toString()
                coroutinesScope.launch {
                    noteDao.update(notes[position])
                }
            }
        }
        holder.time.text = durationString(notes[position].target)
    }

    private fun add(text: String) {
        notes += Note().apply {
            content = text
        }
        notifyItemInserted(notes.lastIndex)
    }

    private fun remove(position: Int) {
        notes -= notes[position]
        notifyItemRemoved(position)
    }
}

