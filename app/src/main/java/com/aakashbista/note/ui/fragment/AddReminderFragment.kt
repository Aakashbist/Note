package com.aakashbista.note.ui.fragment


import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import androidx.work.OneTimeWorkRequest
import androidx.work.WorkManager
import com.aakashbista.note.R
import com.aakashbista.note.appManager.NotificationWorker
import com.aakashbista.note.ui.Extension.toast
import com.aakashbista.note.ui.navigation.NavigationFragment
import kotlinx.android.synthetic.main.add_reminder_fragment.*
import java.text.DateFormat
import java.util.*
import java.util.concurrent.TimeUnit

/**
 * A simple [Fragment] subclass.
 */
class AddReminderFragment : DialogFragment(), TimePickerFragment.TimeSetListener,
    NavigationFragment {


    var calender = Calendar.getInstance(TimeZone.getTimeZone("UTC"))

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.add_reminder_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        action_cancel.setOnClickListener {
            dialog?.dismiss()
        }

        setReminderBotton.setOnClickListener {
            showDatePickerDialog()
        }
         action_ok.setOnClickListener {
             context?.toast("done!!!")
         }

    }

    private fun showDatePickerDialog() {
        AddReminderFragmentDirections.openDatePicker().navigateSafe()
    }

    private fun setAlarm(time: Long) {
        val reminderRequest = OneTimeWorkRequest
            .Builder(NotificationWorker::class.java)
            .setInitialDelay(time, TimeUnit.MILLISECONDS)
            .build()
        WorkManager.getInstance(context!!).enqueue(reminderRequest)
    }


    override fun onTimeSet(year: Int, month: Int, day: Int, hour: Int, minute: Int) {
        setCalender(year, month, day, hour, minute)
        val delay: Long = calender.timeInMillis - System.currentTimeMillis()
        Log.d("data", delay.toString())
        Log.d("data", calender.timeInMillis.toString())
        dateTimeTextView.text = DateFormat.getDateTimeInstance(DateFormat.SHORT,DateFormat.SHORT).format(calender.time)
        setAlarm(delay)
    }

    private fun setCalender(
        year: Int,
        month: Int,
        day: Int,
        hour: Int,
        minute: Int
    ) {
        calender.set(Calendar.YEAR, year)
        calender.set(Calendar.MONTH, month)
        calender.set(Calendar.DAY_OF_MONTH, day)
        calender.set(Calendar.HOUR, hour)
        calender.set(Calendar.MINUTE, minute)
        calender.set(Calendar.SECOND, 0)
    }
}
