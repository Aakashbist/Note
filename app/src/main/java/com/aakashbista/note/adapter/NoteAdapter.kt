package com.aakashbista.note.ui.Adapter

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.DatePicker
import androidx.recyclerview.widget.RecyclerView
import com.aakashbista.note.R
import com.aakashbista.note.db.Note
import kotlinx.android.synthetic.main.note_item.view.*
import java.util.*


class NoteAdapter(
    val itemClickListener: OnItemClickListener
) : RecyclerView.Adapter<NoteAdapter.NoteViewHolder>(), DatePickerDialog.OnDateSetListener {
    private var notes = emptyList<Note>()


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteViewHolder {
        return NoteViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.note_item, parent, false)
        )
    }

    override fun getItemCount() = notes.size

    override fun onBindViewHolder(holder: NoteViewHolder, position: Int) {
        holder.setNotesInView(notes[position], itemClickListener)
//        holder.view.dateTimePickerBtn.setOnClickListener {
//            val datePicker = DatePickerDialog(
//                holder.view.context,
//                DatePickerDialog.OnDateSetListener { view, year, month, dayOfMonth ->
//                },
//                YEAR,
//                MONTH,
//                DAY
//            )
//            datePicker.show()
//
//            val timePicker =
//                TimePickerDialog(
//                    holder.view.context,
//                    TimePickerDialog.OnTimeSetListener { view, hourOfDay, minute ->
//                    },
//                    HOUR,
//                    MINUTE,
//                    true
//                )
//            timePicker.show()
//        }
    }

    override fun onDateSet(view: DatePicker?, year: Int, month: Int, dayOfMonth: Int) {


    }

    fun setItem(notes: List<Note>) {
        this.notes = notes
        notifyDataSetChanged()
    }

    fun getNote(position: Int): Note {
        return notes[position]
    }


    class NoteViewHolder(val view: View) : RecyclerView.ViewHolder(view) {

        fun setNotesInView(note: Note, itemClickListener: OnItemClickListener) {
            view.noteTitle.text = note.title
            view.noteBody.text = note.note
            view.setOnClickListener {
                itemClickListener.onItemClicked(note, view)
            }

        }
    }

    interface OnItemClickListener {
            fun onItemClicked(note: Note, view: View)
    }

}