package com.aakashbista.note.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.aakashbista.note.ui.navigation.NavigationFragment
import com.aakashbista.note.R
import com.aakashbista.note.ui.Adapter.NoteAdapter
import com.aakashbista.note.ui.Extension.toast
import com.aakashbista.note.viewModel.NotesViewModel
import kotlinx.android.synthetic.main.notes_fragment.*

class NotesFragment : Fragment() ,
    NavigationFragment {

    companion object {
        fun newInstance() = NotesFragment()
    }

    private val noteAdapter = NoteAdapter()

    private lateinit var viewModel: NotesViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.notes_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        noteRecyclerView.setHasFixedSize(true)
        noteRecyclerView.layoutManager = LinearLayoutManager(context)
        noteRecyclerView.adapter = noteAdapter
        val itemTouchHelper = ItemTouchHelper(itemTouchHelperCallback)
        itemTouchHelper.attachToRecyclerView(noteRecyclerView)

        noteRecyclerView.adapter = noteAdapter
        viewModel = ViewModelProvider(this).get(NotesViewModel::class.java)

        viewModel.notes.observe(viewLifecycleOwner, Observer { notes ->
            notes?.let {
                noteAdapter.setItem(notes)
            }
        })

        btn_add.setOnClickListener {
            NotesFragmentDirections.actionNotesToAddNotes().navigateSafe()
        }

    }



    private val itemTouchHelperCallback =
        object :
            ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {
            override fun onMove(
                recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val note = noteAdapter.getNote(viewHolder.adapterPosition)
                viewModel.deleteNote(note)
                context?.let {
                    it.toast("Note Deleted")
                }
            }


        }
}
