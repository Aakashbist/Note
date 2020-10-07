package com.aakashbista.note.repository

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.paging.PagingSource
import com.aakashbista.note.db.Note
import com.aakashbista.note.db.NoteDao
import com.aakashbista.note.db.AppDatabase

class NoteRepository(private val application: Application){

     private var noteDao: NoteDao = AppDatabase(application).getNoteDao()

    suspend fun addNote(note: Note) {
                noteDao.addNote(note)
    }

    suspend fun deleteNote(note: Note) {
                noteDao.deleteNote(note)
    }


    fun getPagedNotes(): PagingSource<Int, Note> {
        return noteDao.getPagedNotes()
    }

    suspend fun update(note: Note){
        return noteDao.update(note)
    }
    suspend fun getNoteById(id:Int): Note{
        return noteDao.getNoteById(id)
    }
}
