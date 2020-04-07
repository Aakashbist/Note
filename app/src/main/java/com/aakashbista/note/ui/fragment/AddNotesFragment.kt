package com.aakashbista.note.ui.fragment

import android.app.Application
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.work.OneTimeWorkRequest
import androidx.work.WorkManager
import com.aakashbista.note.R
import com.aakashbista.note.appManager.NotificationWorker
import com.aakashbista.note.db.Note
import com.aakashbista.note.extension.hideKeyboard
import com.aakashbista.note.ui.Extension.toast
import com.aakashbista.note.ui.navigation.NavigationFragment
import com.aakashbista.note.viewModel.AddNotesViewModel
import kotlinx.android.synthetic.main.add_notes_fragment.*
import java.text.DateFormat
import java.util.*
import java.util.concurrent.TimeUnit

class AddNotesFragment : Fragment(),
    NavigationFragment, TimePickerFragment.TimeSetListener {


    var note: Note? = null
    private lateinit var viewModel: AddNotesViewModel
    var utc = TimeZone.getTimeZone("UTC")

    var calender = Calendar.getInstance(utc)


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {


        return inflater.inflate(R.layout.add_notes_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)




        viewModel = ViewModelProvider(this).get(AddNotesViewModel(Application())::class.java)
        arguments?.let {
            note = AddNotesFragmentArgs.fromBundle(it).note
            noteTilte.setText(note?.title)
            noteBody.setText(note?.note)

        }

        btn_save.setOnClickListener {

            val noteTitle = noteTilte.text.toString().trim()
            val body = noteBody.text.toString().trim()

            if (noteTitle.isEmpty()) {
                noteTilte.error = "required"
                noteTilte.setTextColor(Color.RED)
                noteTilte.requestFocus()
                return@setOnClickListener
            }
            if (body.isEmpty()) {
                noteBody.error = "required"
                noteBody.setTextColor(Color.RED)
                noteBody.requestFocus()
                return@setOnClickListener
            }

            context?.let {
                val mNote = Note(noteTitle, body)
                if (note == null) {
                    viewModel.addNote(mNote)
                    it.toast("Note Added")
                } else {
                    mNote.id = note!!.id
                    viewModel.update(mNote)
                    it.toast("Note updated")
                }
            }
            findNavController().navigateUp()
        }

        datepickerbtn.setOnClickListener {

            showDatePickerDialog(reminderTextView.text.toString())
        }
    }


    private fun showDatePickerDialog(date: String) {
        if (date.isNullOrBlank()) {
            AddNotesFragmentDirections.openDatePicker("").navigateSafe()
        } else {
            AddNotesFragmentDirections.openDatePicker(date).navigateSafe()
        }

    }


    private fun setAlarm(diff: Long) {
        val reminderRequest = OneTimeWorkRequest
            .Builder(NotificationWorker::class.java)
            .setInitialDelay(diff, TimeUnit.MILLISECONDS)
            .build()
        WorkManager.getInstance(context!!).enqueue(reminderRequest)

    }

    override fun onBackPressed(): Boolean {
        hideKeyboard()
        return super.onBackPressed()
    }

    override fun onTimeSet(year: Int, month: Int, day: Int, hour: Int, minute: Int) {
        val now = Date(System.currentTimeMillis())
        setCalender(year, month, day, hour, minute)
        val delay: Long = calender.timeInMillis - System.currentTimeMillis()
        reminderTextView.text = DateFormat.getTimeInstance(DateFormat.LONG).format(calender.time)
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
