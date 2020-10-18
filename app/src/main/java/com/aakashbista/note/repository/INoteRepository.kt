package com.aakashbista.note.repository

import androidx.paging.PagingSource
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.aakashbista.note.db.Note

interface INoteRepository {

    suspend fun addNote(note: Note)

    suspend fun update(note: Note)


    suspend fun deleteNote(note: Note)

    fun getPagedNotes(): PagingSource<Int, Note>

    suspend fun getNoteById(id:Int): Note
}