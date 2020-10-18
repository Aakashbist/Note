package com.aakashbista.note.repository

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.paging.PagingSource
import com.aakashbista.note.db.Note
import com.aakashbista.note.db.NoteDao
import com.aakashbista.note.db.AppDatabase

class NoteRepository(application: Application): INoteRepository{

     private var noteDao: NoteDao = AppDatabase(application).getNoteDao()

    override suspend fun addNote(note: Note) {
                noteDao.addNote(note)
    }

    override suspend fun deleteNote(note: Note) {
                noteDao.deleteNote(note)
    }


    override fun getPagedNotes(): PagingSource<Int, Note> {
        return noteDao.getPagedNotes()
    }

    override suspend fun update(note: Note){
        return noteDao.update(note)
    }
    override  suspend fun getNoteById(id:Int): Note{
        return noteDao.getNoteById(id)
    }
}
