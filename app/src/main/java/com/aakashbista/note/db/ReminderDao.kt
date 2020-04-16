package com.aakashbista.note.db

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface ReminderDao {
    @Insert
    suspend fun addReminder(reminder: Reminder)

    @Update
    suspend fun update(reminder: Reminder)

    @Delete
    suspend fun deleteReminder(reminder: Reminder)

    @Query("SELECT  * FROM Reminder ORDER BY id DESC")
    fun getReminder(): LiveData<List<Reminder>>

}