package com.aakashbista.note.ui.fragment

import android.app.Application
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.aakashbista.note.R
import com.aakashbista.note.db.Note
import com.aakashbista.note.extension.hideKeyboard
import com.aakashbista.note.ui.Extension.toast
import com.aakashbista.note.ui.navigation.NavigationFragment
import com.aakashbista.note.viewModel.NotesViewModel
import kotlinx.android.synthetic.main.add_notes_fragment.*

class AddNotesFragment : Fragment(),
    NavigationFragment {

    var note: Note? = null
    private lateinit var viewModel: NotesViewModel


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.add_notes_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(NotesViewModel(Application())::class.java)

        arguments?.let {
            note = AddNotesFragmentArgs.fromBundle(it).note
            reminderTitle.setText(note?.title)
            reminderDescription.setText(note?.note)
        }

        btn_save.setOnClickListener {
            if (saveNote()) return@setOnClickListener
            findNavController().navigateUp()
        }
    }

    private fun saveNote(): Boolean {
        val title = reminderTitle.text.toString().trim()
        val body = reminderDescription.text.toString().trim()

        if (title.isEmpty()) {
            reminderTitle.error = "required"
            reminderTitle.setTextColor(Color.RED)
            reminderTitle.requestFocus()
            return true
        }
        if (body.isEmpty()) {
            reminderDescription.error = "required"
            reminderDescription.setTextColor(Color.RED)
            reminderDescription.requestFocus()
            return true
        }

        context?.let {
            val mNote = Note(title, body)
            if (note == null) {
                viewModel.addNote(mNote)
                //  requireView().snackbar("Note Added")
                it.toast("Note added")
            } else {
                mNote.id = note!!.id
                viewModel.update(mNote)
                it.toast("Note updated")
            }
            hideKeyboard()
        }
        return false
    }

    override fun onBackPressed(): Boolean {
        hideKeyboard()
        return super.onBackPressed()
    }
}
