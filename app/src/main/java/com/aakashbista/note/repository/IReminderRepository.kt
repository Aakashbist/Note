package com.aakashbista.note.repository

import androidx.lifecycle.LiveData
import com.aakashbista.note.db.Reminder

interface IReminderRepository {

    suspend fun addReminder(reminder: Reminder)

    suspend fun deleteReminder(reminder: Reminder)

    fun getReminders(): LiveData<List<Reminder>>

    suspend fun update(reminder: Reminder)
}