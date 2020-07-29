package com.aakashbista.note.ui.Adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.RecyclerView
import com.aakashbista.note.R
import com.aakashbista.note.db.Note
import com.aakashbista.note.extension.changeColor
import com.google.android.material.floatingactionbutton.FloatingActionButton
import kotlinx.android.synthetic.main.note_item.view.*


class NoteAdapter(
    private val itemClickListener: OnItemClickListener,
    private val lifecycleOwner: LifecycleOwner,
    private val selectedNotes: LiveData<List<Note>>

) : RecyclerView.Adapter<NoteAdapter.NoteViewHolder>() {
    private var notes = emptyList<Note>()


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteViewHolder {
        return NoteViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.note_item, parent, false),
            selectedNotes,
            lifecycleOwner
        )
    }

    override fun getItemCount() = notes.size

    override fun onBindViewHolder(holder: NoteViewHolder, position: Int) {
        holder.setNotesInView(notes[position], itemClickListener)
    }


    fun setItem(notes: List<Note>) {
        this.notes = notes
        notifyDataSetChanged()
    }

    fun getNote(position: Int): Note {
        return notes[position]
    }


    class NoteViewHolder(
        val view: View,
        private val selectedNotes: LiveData<List<Note>>,
        private val lifecycleOwner: LifecycleOwner
    ) : RecyclerView.ViewHolder(view) {

        private lateinit var _note: Note

        fun setNotesInView(note: Note, itemClickListener: OnItemClickListener) {

            view.reminderTitle.text = note.title
            view.reminderDescription.text = note.note

            view.setOnClickListener {
                itemClickListener.onItemClicked(note)
            }

            view.setOnLongClickListener {
                itemClickListener.activateMultiSelectionMode()
                itemClickListener.onItemClicked(note)
                true
            }

            _note = note
            selectedNotes.observe(lifecycleOwner, Observer { notes ->

                if (notes != null) {
                    if (notes.contains(note)) {
                        itemView.cardView.changeColor(newColor =  R.color.darkGrey)
                    } else {
                        itemView.cardView.changeColor(newColor = R.color.white)
                    }
                } else {

                    itemView.cardView.changeColor(newColor = R.color.white)
                }
            })
        }
    }

    interface OnItemClickListener {
        fun onItemClicked(note: Note)
        fun isMultiSelectionModeEnabled(): Boolean
        fun activateMultiSelectionMode()
    }

}