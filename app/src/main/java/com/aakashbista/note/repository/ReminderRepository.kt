package com.aakashbista.note.repository

import android.app.Application
import androidx.lifecycle.LiveData
import com.aakashbista.note.db.AppDatabase
import com.aakashbista.note.db.Reminder
import com.aakashbista.note.db.ReminderDao

class ReminderRepository(application: Application) : IReminderRepository {

    private var reminderDao: ReminderDao = AppDatabase(application).getReminderDao()


    override suspend fun addReminder(reminder: Reminder) {
        reminderDao.addReminder(reminder)
    }

    override suspend fun deleteReminder(reminder: Reminder) {
        reminderDao.deleteReminder(reminder)
    }

    override fun getReminders(): LiveData<List<Reminder>> {
        return reminderDao.getReminders()

    }

    override   suspend fun update(reminder: Reminder) {
        return reminderDao.update(reminder)
    }

}