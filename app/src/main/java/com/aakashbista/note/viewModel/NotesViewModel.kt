package com.aakashbista.note.viewModel

import android.app.Application
import androidx.lifecycle.LiveData
import com.aakashbista.note.db.Note
import com.aakashbista.note.repository.NoteRepository
import com.aakashbista.note.ui.Helper.BaseViewModel
import kotlinx.coroutines.launch

class NotesViewModel(application: Application) : BaseViewModel(application) {

    private var repository: NoteRepository = NoteRepository(application)

    lateinit var notes: LiveData<List<Note>>

    init {
       repository = NoteRepository(application)
        getAllNotes()
    }

    private fun getAllNotes() {
        notes = repository.getNotes()

    }

    public fun deleteNote(note:Note){
        launch {
             repository.deleteNote(note)
        }

    }


}
