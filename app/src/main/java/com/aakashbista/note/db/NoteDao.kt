package com.aakashbista.note.db

import androidx.lifecycle.LiveData
import androidx.paging.PagingSource
import androidx.room.*

@Dao
interface NoteDao {
    @Insert
    suspend fun addNote(note: Note)

    @Update
    suspend fun update(note: Note)

    @Delete
    suspend fun deleteNote(note: Note)

    @Query("SELECT  * FROM Note ORDER BY id DESC")
    fun getPagedNotes(): PagingSource<Int, Note>

    @Query("SELECT * FROM Note WHERE id=:id ")
    suspend fun getNoteById(id:Int): Note
}