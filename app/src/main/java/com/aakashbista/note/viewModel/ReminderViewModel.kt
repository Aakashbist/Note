package com.aakashbista.note.viewModel

import android.app.Application
import androidx.lifecycle.LiveData
import com.aakashbista.note.BaseViewModel
import com.aakashbista.note.db.Reminder
import com.aakashbista.note.repository.ReminderRepository
import kotlinx.coroutines.launch

class ReminderViewModel(application: Application):BaseViewModel(application) {

    val repository:ReminderRepository= ReminderRepository(application)
    lateinit var reminders:LiveData<List<Reminder>>

    init {
        getAllReminders()
    }


    private fun getAllReminders() {
        reminders=repository.getReminders()
    }

    fun deleteReminder(reminder:Reminder){
        launch{
            repository.deleteReminder(reminder)
        }

    }

}