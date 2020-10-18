package com.aakashbista.note.ui.Adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.AsyncListDiffer
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
) : PagingDataAdapter<Note, NoteAdapter.NoteViewHolder>(DIFF_CALLBACK) {

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
            view.reminderTitle.text = _note?.title
            view.reminderDescription.text = _note?.note

            view.setOnClickListener {
                itemClickListener.onItemClicked(_note!!)
            }

            view.setOnLongClickListener {
                itemClickListener.activateMultiSelectionMode()
                itemClickListener.onItemClicked(_note!!)
                true
            }

            selectedNotes.observe(lifecycleOwner, Observer { notes ->

                if (notes != null) {
                    if (notes.contains(_note!!)) {
                        itemView.cardView.changeColor(newColor = R.color.darkGrey)
                    } else {
                        itemView.cardView.changeColor(newColor = R.color.white)
                    }
                } else {

                    itemView.cardView.changeColor(newColor = R.color.white)
                }
            })
        }
    }

    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<Note>() {
            override fun areItemsTheSame(oldItem: Note, newItem: Note): Boolean =
                oldItem.id == newItem.id

            override fun areContentsTheSame(oldItem: Note, newItem: Note): Boolean =
                oldItem == newItem
        }
    }

    interface OnItemClickListener {
        fun onItemClicked(note: Note)
        fun isMultiSelectionModeEnabled(): Boolean
        fun activateMultiSelectionMode()
    }
}