package com.aakashbista.note.repository

import android.app.Application
import androidx.lifecycle.LiveData
import com.aakashbista.note.db.AppDatabase
import com.aakashbista.note.db.Reminder
import com.aakashbista.note.db.ReminderDao

class ReminderRepository(private val application: Application) {

    private lateinit var reminderDao: ReminderDao

    init {
        reminderDao = AppDatabase(application).getReminderDao()
    }


    suspend fun addReminder(reminder: Reminder) {
        reminderDao.addReminder(reminder)
    }

    suspend fun deleteReminder(reminder: Reminder) {
        reminderDao.deleteReminder(reminder)
    }

    fun getReminders(): LiveData<List<Reminder>> {
        return reminderDao.getReminders()

    }

    suspend fun update(reminder: Reminder) {
        return reminderDao.update(reminder)
    }

}