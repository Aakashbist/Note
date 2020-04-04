package com.aakashbista.note.ui.Adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.aakashbista.note.db.Note
import com.aakashbista.note.R
import com.aakashbista.note.ui.fragment.NotesFragmentDirections
import kotlinx.android.synthetic.main.note_item.view.*

class NoteAdapter() : RecyclerView.Adapter<NoteAdapter.NoteViewHolder>() {
    private var notes= emptyList<Note>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteViewHolder {
        return NoteViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.note_item, parent, false)
        )
    }

    override fun getItemCount() = notes.size

    override fun onBindViewHolder(holder: NoteViewHolder, position: Int) {
        holder.setNotesInView(notes[position])
        holder.view.setOnClickListener {
          var action=NotesFragmentDirections.actionNotesToAddNotes(notes[position])
           Navigation.findNavController(it).navigate(action)
        }
    }

    fun setItem( notes: List<Note>) {
        this.notes = notes
        notifyDataSetChanged()
    }

    fun getNote(position:Int):Note {
        return notes[position]
    }


    class NoteViewHolder(val view: View) : RecyclerView.ViewHolder(view) {

        fun setNotesInView(note: Note) {
            view.noteTitle.text = note.title
            view.noteBody.text = note.note

        }
    }
}