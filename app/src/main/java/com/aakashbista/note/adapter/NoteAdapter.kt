package com.aakashbista.note.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.aakashbista.note.R
import com.aakashbista.note.db.Note
import com.aakashbista.note.extension.changeColor
import kotlinx.android.synthetic.main.note_item.view.*


class NoteAdapter(
    private val itemClickListener: OnItemClickListener,
    private val lifecycleOwner: LifecycleOwner,
    private val selectedNotes: LiveData<List<Note>>
) : PagingDataAdapter<Note, NoteAdapter.NoteViewHolder>(NoteDiffUtil()) {

    companion object {
        private const val SELECTED_COLOR = R.color.darkGrey
        private const val UNSELECTED_COLOR = R.color.white
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteViewHolder {
        return NoteViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.note_item, parent, false),
            selectedNotes,
            lifecycleOwner
        )
    }

    override fun onBindViewHolder(holder: NoteViewHolder, position: Int) {
        getItem(position)?.let { holder.setNotesInView(it, itemClickListener) }
    }

    class NoteViewHolder(
        val view: View,
        private val selectedNotes: LiveData<List<Note>>,
        private val lifecycleOwner: LifecycleOwner
    ) : RecyclerView.ViewHolder(view) {

        var _note: Note? = null

        fun setNotesInView(note: Note, itemClickListener: OnItemClickListener) {
            this._note = note
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

            selectedNotes.observe(lifecycleOwner, Observer { notes ->
                itemView.cardView.changeColor(
                    newColor = if (notes != null && notes.contains(note)) {
                        SELECTED_COLOR
                    } else {
                        UNSELECTED_COLOR
                    }
                )
            })
        }
    }

    class NoteDiffUtil : DiffUtil.ItemCallback<Note>() {
        override fun areItemsTheSame(oldItem: Note, newItem: Note): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Note, newItem: Note): Boolean {
            return oldItem == newItem
        }
    }

    interface OnItemClickListener {
        fun onItemClicked(note: Note)
        fun isMultiSelectionModeEnabled(): Boolean
        fun activateMultiSelectionMode()
    }
}