package com.aakashbista.note.ui.fragment

import android.app.Application
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.aakashbista.note.ui.navigation.NavigationFragment
import com.aakashbista.note.R
import com.aakashbista.note.db.Note
import com.aakashbista.note.extension.hideKeyboard
import com.aakashbista.note.ui.Extension.toast
import com.aakashbista.note.viewModel.AddNotesViewModel
import kotlinx.android.synthetic.main.add_notes_fragment.*

class AddNotesFragment : Fragment() ,
    NavigationFragment {


    var note: Note? = null
    private lateinit var viewModel: AddNotesViewModel
    override fun onCreateView( inflater: LayoutInflater, container: ViewGroup?,savedInstanceState: Bundle?
    ): View? {


        return inflater.inflate(R.layout.add_notes_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(AddNotesViewModel(Application())::class.java)
        arguments?.let {
            note = AddNotesFragmentArgs.fromBundle(it).note
            noteTilte.setText(note?.title)
            noteBody.setText(note?.note)

        }
        btn_save.setOnClickListener {

            val noteTitle = noteTilte.text.toString().trim()
            val body = noteBody.text.toString().trim()

            if (noteTitle.isEmpty()) {
                noteTilte.error = "required"
                noteTilte.setTextColor(Color.RED)
                noteTilte.requestFocus()
                return@setOnClickListener
            }
            if (body.isEmpty()) {
                noteBody.error = "required"
                noteBody.setTextColor(Color.RED)
                noteBody.requestFocus()
                return@setOnClickListener
            }

            context?.let {
                val mNote = Note(noteTitle, body)
                if (note == null) {
                    viewModel.addNote(mNote)
                    it.toast("Note Added")
                } else {
                    mNote.id = note!!.id
                    viewModel.update(mNote)
                    it.toast("Note updated")
                }
            }

        AddNotesFragmentDirections.actionAddNotesToNotes().navigateSafe()
       }

    }

    override fun onBackPressed(): Boolean {
        hideKeyboard()
       return super.onBackPressed()
    }
}
