package com.aakashbista.note.ViewModel

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.aakashbista.note.DB.Note
import com.aakashbista.note.Repository.NoteRepository
import com.aakashbista.note.UI.Helper.BaseViewModel
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
