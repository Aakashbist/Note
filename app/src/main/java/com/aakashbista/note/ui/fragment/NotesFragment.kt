package com.aakashbista.note.ui.fragment

import android.content.Intent
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.paging.PagingData
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.aakashbista.note.R
import com.aakashbista.note.db.Note
import com.aakashbista.note.state.ToolbarState
import com.aakashbista.note.adapter.NoteAdapter
import com.aakashbista.note.adapter.NoteAdapter.OnItemClickListener
import com.aakashbista.note.ui.Extension.toast
import com.aakashbista.note.ui.navigation.NavigationFragment
import com.aakashbista.note.viewModel.NotesViewModel
import kotlinx.android.synthetic.main.notes_fragment.*
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class NotesFragment : Fragment(), NavigationFragment, OnItemClickListener {

    private lateinit var noteAdapter: NoteAdapter
    private lateinit var viewModel: NotesViewModel
    private var actionMode: ActionMode? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.notes_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(NotesViewModel::class.java)
        noteRecyclerView.setHasFixedSize(true)
        noteRecyclerView.layoutManager = LinearLayoutManager(context)
        noteAdapter = NoteAdapter(
            this,
            viewLifecycleOwner,
            viewModel.selectedNotes
        )
        noteRecyclerView.adapter = noteAdapter
        val itemTouchHelper = ItemTouchHelper(itemTouchHelperCallback)
        itemTouchHelper.attachToRecyclerView(noteRecyclerView)

        lifecycleScope.launch {
            viewModel.noteLists.collect { it: PagingData<Note> ->
                noteAdapter.submitData(it)
            }
        }

        btn_add.setOnClickListener {
            NotesFragmentDirections.openNote().navigateSafe()
        }

        setUpObserver()
    }

    private fun setUpObserver() {
        viewModel.toolbarState.observe(viewLifecycleOwner, Observer { state ->
            state?.let {
                when (state) {
                    ToolbarState.NormalViewState -> setNormalToolbar()
                    ToolbarState.MultiSelectionState -> setMultiSelectToolbar()
                }
            }
        })
    }

    private fun setMultiSelectToolbar() {
        btn_add.hide()
        if (actionMode == null) {
            actionMode = activity?.startActionMode(ActionModeCallback())
        }
    }

    private fun setNormalToolbar() {
        viewModel.clearSelectedNotes()
        actionMode?.finish()
        btn_add.show()
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
                (viewHolder as NoteAdapter.NoteViewHolder)._note?.let {
                    viewModel.deleteNote(it)
                }
                context?.let {
                    it.toast("Note Deleted")
                }
            }
        }

    inner class ActionModeCallback : ActionMode.Callback {
        override fun onActionItemClicked(mode: ActionMode?, item: MenuItem?): Boolean {
            when (item?.itemId) {
                R.id.action_delete -> {
                    val selectedNotes = viewModel.getSelectedNotes()
                    selectedNotes.forEach { note ->
                        viewModel.deleteNote(note)
                    }
                    viewModel.setToolbarState(ToolbarState.NormalViewState)
                    context?.toast("deleted")
                }

                R.id.action_share -> {
                    val builder = StringBuilder()
                    val selectedNotes = viewModel.getSelectedNotes()
                    for (note in selectedNotes) {

                        builder.append(note.title)
                            .append("\n")
                            .append(note.note)
                            .append("\n\n")
                    }
                    val sendIntent: Intent = Intent().apply {
                        action = Intent.ACTION_SEND
                        putExtra(Intent.EXTRA_TEXT, builder.toString())
                        type = "text/plain"
                    }
                    val shareIntent = Intent.createChooser(sendIntent, null)
                    viewModel.setToolbarState(ToolbarState.NormalViewState)
                    startActivity(shareIntent)
                }
            }
            return false
        }

        override fun onCreateActionMode(mode: ActionMode?, menu: Menu?): Boolean {
            mode?.menuInflater?.inflate(R.menu.multi_selected_menu, menu)
            viewModel.selectedNotes.observe(viewLifecycleOwner, Observer {
                var size = viewModel.getSelectedNotes().size
                if (size > 0) {
                    mode?.title = "$size  ${"Selected"}"
                } else {
                    mode?.title = ""
                }
            })
            return true
        }

        override fun onPrepareActionMode(mode: ActionMode?, menu: Menu?): Boolean {
            return false
        }

        override fun onDestroyActionMode(mode: ActionMode?) {
            viewModel.setToolbarState(ToolbarState.NormalViewState)
            actionMode = null
        }
    }

    override fun activateMultiSelectionMode() =
        viewModel.setToolbarState(ToolbarState.MultiSelectionState)

    override fun isMultiSelectionModeEnabled() = viewModel.isMultiSelectionStateActive()

    override fun onItemClicked(note: Note) {
        if (isMultiSelectionModeEnabled()) {
            viewModel.addOrRemoveNoteFromSelectedList(note)
        } else {
            NotesFragmentDirections.openNote(note).navigateSafe()
        }
    }
}


