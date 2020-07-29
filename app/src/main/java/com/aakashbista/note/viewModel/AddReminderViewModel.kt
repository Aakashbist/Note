package com.aakashbista.note.viewModel

import android.app.Application
import com.aakashbista.note.BaseViewModel
import com.aakashbista.note.db.Reminder
import com.aakashbista.note.repository.ReminderRepository
import kotlinx.coroutines.launch

class AddReminderViewModel(application: Application) : BaseViewModel(application) {

    var repository: ReminderRepository = ReminderRepository(application)


    fun addReminder(reminder:Reminder) {
        launch {
            repository.addReminder(reminder)
        }

    }


    fun update(reminder:Reminder){
        launch {
            repository.update(reminder)
        }
    }
}