package com.aakashbista.note.ui.fragment


import android.app.Application
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.work.Data
import androidx.work.OneTimeWorkRequest
import androidx.work.WorkManager
import com.aakashbista.note.R
import com.aakashbista.note.appManager.NotificationWorker
import com.aakashbista.note.db.Reminder
import com.aakashbista.note.ui.Extension.toast
import com.aakashbista.note.ui.navigation.NavigationFragment
import com.aakashbista.note.viewModel.AddReminderViewModel
import kotlinx.android.synthetic.main.add_reminder_fragment.*
import java.text.DateFormat
import java.time.Duration
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*
import java.util.concurrent.TimeUnit

/**
 * A simple [Fragment] subclass.
 */
class AddReminderFragment : DialogFragment(), TimePickerFragment.TimeSetListener,
    NavigationFragment {

    var reminder: Reminder? = null

    lateinit var viewModel: AddReminderViewModel
    private  var localDateTime: LocalDateTime? =null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.add_reminder_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(AddReminderViewModel(Application())::class.java)

        arguments?.let {
            reminder = AddReminderFragmentArgs.fromBundle(it).reminder
            if(reminder!=null) {
                title.setText(reminder?.title)
                reminderDescription.setText(reminder?.description)
                val dateTimeFormatter= DateTimeFormatter.ofPattern("MM/dd/yy hh:mm a")
                dateTimeTextView.setText(reminder?.dateTime?.format(dateTimeFormatter))
            }
        }

        action_cancel.setOnClickListener {
            dialog?.dismiss()
        }

        setReminderBotton.setOnClickListener {
            showDatePickerDialog()
        }
        action_ok.setOnClickListener {

            if (saveReminder()) return@setOnClickListener
            dialog?.dismiss()
        }
    }

    private fun saveReminder(): Boolean {
        val reminderTitle = title.text.toString().trim()
        val description = reminderDescription.text.toString().trim()
        val reminderDateTime = dateTimeTextView.text.toString().trim()

        if (reminderTitle.isEmpty()) {
            title.error = "title required"
            title.setTextColor(Color.RED)
            title.requestFocus()
            return true
        }
        if (description.isEmpty()) {
            reminderDescription.error = "description required"
            reminderDescription.setTextColor(Color.RED)
            reminderDescription.requestFocus()
            return true
        }

        if (reminderDateTime.isEmpty()) {
            dateTimeTextView.error = "choose date and time"
            dateTimeTextView.setTextColor(Color.RED)
            dateTimeTextView.requestFocus()
            return true
        }

        context?.let {
            val mReminder =  Reminder(reminderTitle, description, localDateTime!!)
            if (reminder == null) {
                viewModel.addReminder(mReminder!!)
                it.toast("Reminder Added")
            } else {
                mReminder?.id = reminder!!.id
                viewModel.update(mReminder!!)
                it.toast("Reminder updated")
            }
        }

        val delay: Duration =  Duration.between( LocalDateTime.now(),localDateTime)
        Log.d("date",delay.toMillis().toString() + " "+ localDateTime+ " "+  LocalDateTime.now())
        setAlarm(delay, reminderTitle, description)
        return false
    }

    private fun showDatePickerDialog() {
        AddReminderFragmentDirections.openDatePicker(reminder?.dateTime).navigateSafe()
    }


    private fun setAlarm(time: Duration, reminderTitle: String, description: String) {
        val reminderRequest = OneTimeWorkRequest
            .Builder(NotificationWorker::class.java)
            .setInputData(createInputData(reminderTitle, description))
            .addTag("${title}")
            .setInitialDelay(time.toMillis(), TimeUnit.MILLISECONDS)
            .build()
        WorkManager.getInstance(context!!).enqueue(reminderRequest)
        WorkManager.getInstance(context!!).getWorkInfoByIdLiveData(reminderRequest.id)
    }

    private fun createInputData(reminderTitle: String, description: String): Data {
        return Data.Builder()
            .putString("TITLE_KEY", reminderTitle)
            .putString("DESCRIPTION_KEY", description)
            .build()
    }

    override fun onTimeSet(year: Int, month: Int, day: Int, hour: Int, minute: Int) {
        localDateTime= LocalDateTime.of(year,month,day,hour, minute)
        val dateTimeFormatter= DateTimeFormatter.ofPattern("MM/dd/yy hh:mm a")
        dateTimeTextView.text = localDateTime?.format(dateTimeFormatter)
    }

}
