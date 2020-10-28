package com.aakashbista.note.ui.fragment


import android.app.Application
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.work.OneTimeWorkRequest
import androidx.work.WorkManager
import com.aakashbista.note.R
import com.aakashbista.note.appManager.NotificationWorker
import com.aakashbista.note.db.Reminder
import com.aakashbista.note.extension.createDate
import com.aakashbista.note.extension.formatDate
import com.aakashbista.note.ui.Extension.toast
import com.aakashbista.note.ui.navigation.NavigationFragment
import com.aakashbista.note.viewModel.ReminderViewModel
import kotlinx.android.synthetic.main.add_reminder_fragment.*
import java.time.Duration
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.concurrent.TimeUnit

/**
 * A simple [Fragment] subclass.
 */
class AddReminderFragment : DialogFragment(), TimePickerFragment.TimeSetListener,
    NavigationFragment {

    var reminder: Reminder? = null
    lateinit var viewModel: ReminderViewModel
    private var localDateTime: LocalDateTime? = null

//    TODO implement material dialog
//    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
//        return super.onCreateDialog(savedInstanceState)
//    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.add_reminder_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(ReminderViewModel(Application())::class.java)

        arguments?.let {
            reminder = AddReminderFragmentArgs.fromBundle(it).reminder
            if (reminder != null) {
                localDateTime = reminder?.dateTime
                title.setText(reminder?.title)
                reminderDescription.setText(reminder?.description)
                dateTimeTextView.setText(reminder?.dateTime?.formatDate())
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
            val delay: Duration = Duration.between(LocalDateTime.now(), localDateTime)
            var mReminder = Reminder(
                reminderTitle,
                description,
                localDateTime!!
            )
            val reminderNotificationWorkRequestId = setAlarm(delay, mReminder)
            mReminder = mReminder.copy(workRequestId = reminderNotificationWorkRequestId)
            if (reminder == null) {
                viewModel.addReminder(mReminder)
//                parentFragment?.requireView()?.let { view -> view.snackbar("Reminder Added") }
                it.toast("Reminder Added")
            } else {
                mReminder.id = reminder!!.id
                viewModel.update(mReminder)
//         parentFragment?.requireView()?.let { view -> view.snackbar("Reminder Added") }
                it.toast("Reminder updated")
            }
        }
        return false
    }

    private fun showDatePickerDialog() {
        AddReminderFragmentDirections.openDatePicker(reminder?.dateTime).navigateSafe()
    }

    private fun setAlarm(time: Duration, reminder: Reminder): String {
        val reminderRequest = OneTimeWorkRequest
            .Builder(NotificationWorker::class.java)
            .setInputData(reminder.createDate())
            .setInitialDelay(time.toMillis(), TimeUnit.MILLISECONDS)
            .build()

        val instance = WorkManager.getInstance(requireContext())
        instance.enqueue(reminderRequest)
        return reminderRequest.id.toString()
    }

    override fun onTimeSet(year: Int, month: Int, day: Int, hour: Int, minute: Int) {
        localDateTime = LocalDateTime.of(year, month, day, hour, minute)
        val dateTimeFormatter = DateTimeFormatter.ofPattern("MM/dd/yy hh:mm a")
        dateTimeTextView.text = localDateTime?.format(dateTimeFormatter)
    }
}
