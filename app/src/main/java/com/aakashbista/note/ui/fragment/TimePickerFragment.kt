package com.aakashbista.note.ui.fragment

import android.app.Dialog
import android.app.TimePickerDialog
import android.os.Bundle
import android.os.Parcelable
import android.text.format.DateFormat
import android.widget.TimePicker
import androidx.fragment.app.DialogFragment
import com.aakashbista.note.extension.getParentFragmentListener
import kotlinx.android.parcel.Parcelize
import java.util.*

class TimePickerFragment : DialogFragment(), TimePickerDialog.OnTimeSetListener {

    interface TimeSetListener {
        fun onTimeSet(year: Int, month: Int, day: Int, hour: Int, minute: Int)
    }

    val parent: TimeSetListener
        get() = getParentFragmentListener<TimeSetListener>(true)

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val c = Calendar.getInstance()
        val hour = c.get(Calendar.HOUR_OF_DAY)
        val minute = c.get(Calendar.MINUTE)
        return TimePickerDialog(activity, this, hour, minute, DateFormat.is24HourFormat(activity))
    }

    override fun onTimeSet(view: TimePicker?, hourOfDay: Int, minute: Int) {
        arguments?.let {
            val timePickerData = TimePickerFragmentArgs.fromBundle(it).data
            parent.onTimeSet(
                timePickerData.year,
                timePickerData.month,
                timePickerData.day,
                hourOfDay,
                minute
            )
        }

    }
}

@Parcelize
data class TimePickerData(
    val year: Int,
    val month: Int,
    val day: Int
) : Parcelable
