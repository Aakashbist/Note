package com.aakashbista.note.UI.Adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.aakashbista.note.DB.Note
import com.aakashbista.note.R
import com.aakashbista.note.UI.Extension.toast
import com.aakashbista.note.UI.Fragment.NotesFragmentDirections
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
          var action=NotesFragmentDirections.actionNotesToAddNotes(notes[position].id)
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