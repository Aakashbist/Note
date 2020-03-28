package com.aakashbista.note.UI.ViewModel

import android.app.Application
import com.aakashbista.note.Repository.NoteRepository
import com.aakashbista.note.DB.Note
import com.aakashbista.note.UI.Helper.BaseViewModel
import kotlinx.coroutines.async
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
