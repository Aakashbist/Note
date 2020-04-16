package com.aakashbista.note.repository

import android.app.Application
import androidx.lifecycle.LiveData
import com.aakashbista.note.db.Note
import com.aakashbista.note.db.NoteDao
import com.aakashbista.note.db.AppDatabase

class NoteRepository(private val application: Application){

     private lateinit var noteDao: NoteDao

    init {
        noteDao = AppDatabase(application).getNoteDao()
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
