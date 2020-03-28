package com.aakashbista.note.Repository

import android.app.Application
import androidx.lifecycle.LiveData
import com.aakashbista.note.DB.Note
import com.aakashbista.note.DB.NoteDao
import com.aakashbista.note.DB.NoteDatabase

class NoteRepository(private val application: Application){

     private lateinit var noteDao: NoteDao

    init {
        noteDao = NoteDatabase(application).getNoteDao()
    }

    suspend fun addNote(note: Note) {
                noteDao.addNote(note)
    }

    suspend fun deleteNote(note: Note) {
                noteDao.deleteNote(note)
    }

     fun getNotes(): LiveData<List<Note>> {
          return noteDao.getNotes()

    }

    suspend fun update(note: Note){
        return noteDao.update(note)
    }
    suspend fun getNoteById(id:Int): Note{
        return noteDao.getNoteById(id)
    }
}
