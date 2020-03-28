package com.aakashbista.note.viewModel

import android.app.Application
import com.aakashbista.note.repository.NoteRepository
import com.aakashbista.note.db.Note
import com.aakashbista.note.ui.Helper.BaseViewModel
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking


class AddNotesViewModel(application: Application) : BaseViewModel(application) {

    var repository: NoteRepository = NoteRepository(application)


    fun addNote(note: Note) {
        launch {
            repository.addNote(note)
        }

    }

    fun getNoteById(id:Int): Note = runBlocking{
        repository.getNoteById(id)
     }


     fun update(note: Note){
         launch {
             repository.update(note)
         }
     }
}
